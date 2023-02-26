package no.sd.sdbot.cron

import no.sd.pubg.domain.Player
import no.sd.pubg.domain.Season
import no.sd.pubg.service.PlayerService
import no.sd.pubg.service.SeasonService
import no.sd.sdbot.db.DbService
import no.sd.sdbot.discord.Bot
import no.sd.sdbot.discord.ChannelId
import no.sd.sdbot.discord.utility.print.userListToPrint
import no.sd.sdbot.discord.utility.stringSetToStringWithDelim
import no.sd.sdbot.discord.utility.userListToStringSetWithNames
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdatePUBGStats(
        val dbService: DbService,
        val seasonService: SeasonService,
        val playerService: PlayerService,
        val bot: Bot
) {

    @Scheduled(fixedRate = 1800000)
    fun getPUBGStats() {
        val currentSeason: Season = seasonService.getCurrentSeason()

        val uniquePlayers: Set<String> = userListToStringSetWithNames(dbService.getAllUsers())
        val players: Set<Player> = playerService.getPlayersByNames(stringSetToStringWithDelim(uniquePlayers, ","))

        for (player in players) {
            player.rankedStats = seasonService.getRankedStats(player.id, currentSeason.seasonId)
            dbService.updateGuestRankAdr(player.name, player.rankedStats!!.adr, player.rankedStats!!.rank)
            dbService.updateSDRankAdr(player.name, player.rankedStats!!.adr, player.rankedStats!!.rank)
        }

        val newGuestHighscoreList: String = userListToPrint(dbService.getAllGuestUsers())
        val newSDHighscoreList: String = userListToPrint(dbService.getAllSDUsers())
        println(newGuestHighscoreList)
        println(newSDHighscoreList)
        val deleteLastMsg = true
        bot.sendMessage(newGuestHighscoreList, ChannelId.GUEST_HIGHSCORE_CHANNEL, deleteLastMsg)
        bot.sendMessage(newSDHighscoreList, ChannelId.SD_HIGHSCORE_CHANNEL, deleteLastMsg)
    }
}