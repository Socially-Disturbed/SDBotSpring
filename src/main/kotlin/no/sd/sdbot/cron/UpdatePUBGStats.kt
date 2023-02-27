package no.sd.sdbot.cron

import no.sd.pubg.domain.Player
import no.sd.pubg.domain.Season
import no.sd.pubg.service.PubgService
import no.sd.sdbot.db.DbService
import no.sd.sdbot.db.Warrior
import no.sd.sdbot.discord.Bot
import no.sd.sdbot.discord.ChannelId
import no.sd.sdbot.discord.utility.print.prettyPrint
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdatePUBGStats(
        val dbService: DbService,
        val pubgService: PubgService,
        val bot: Bot
) {

    @Scheduled(fixedRate = 36000000)
    fun getPUBGStats() {
        val currentSeason: Season = pubgService.getCurrentSeason()

        val allWarriors: List<Warrior> = dbService.getAllWarriors()
        val players: Set<Player> = pubgService.getPlayersByNames(allWarriors.joinToString(","))

        for (player in players) {
            player.rankedStats = pubgService.getRankedStats(player.id, currentSeason.seasonId)
            dbService.updateGuestRankAdr(player.name, player.rankedStats!!.adr, player.rankedStats!!.rank)
            dbService.updateSDRankAdr(player.name, player.rankedStats!!.adr, player.rankedStats!!.rank)
        }

        val newGuestHighscoreList: String = dbService.getAllGuestUsers().joinToString { it.prettyPrint() }
        val newSDHighscoreList: String = dbService.getAllSDUsers().joinToString { it.prettyPrint() }
        println(newGuestHighscoreList)
        println(newSDHighscoreList)
        val deleteLastMsg = true
        bot.sendMessage(newGuestHighscoreList, ChannelId.GUEST_HIGHSCORE_CHANNEL, deleteLastMsg)
        bot.sendMessage(newSDHighscoreList, ChannelId.SD_HIGHSCORE_CHANNEL, deleteLastMsg)
    }
}