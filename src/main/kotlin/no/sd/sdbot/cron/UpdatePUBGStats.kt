package no.sd.sdbot.cron

import discord4j.core.spec.EmbedCreateSpec
import discord4j.rest.util.Color
import no.sd.pubg.domain.Player
import no.sd.pubg.domain.Season
import no.sd.pubg.service.PlayerService
import no.sd.pubg.service.SeasonService
import no.sd.sdbot.db.DbService
import no.sd.sdbot.db.User
import no.sd.sdbot.discord.Bot
import no.sd.sdbot.discord.ChannelId
import no.sd.sdbot.discord.utility.stringSetToStringWithDelim
import no.sd.sdbot.discord.utility.userListToStringSetWithNames
import no.sd.sdbot.discord.utility.usersToEmbedField
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdatePUBGStats(
        val dbService: DbService,
        val seasonService: SeasonService,
        val playerService: PlayerService,
        val bot: Bot
) {

    @Scheduled(fixedRate = 36000000)
    fun getPUBGStats() {
        var pubgRequestCounter = 0
        val deleteLastMsg = true
        val currentSeason: Season = seasonService.getCurrentSeason()
        pubgRequestCounter++

        val uniquePlayers: Set<String> = userListToStringSetWithNames(dbService.getAllUsers())
        val players: Set<Player> = playerService.getPlayersByNames(stringSetToStringWithDelim(uniquePlayers, ","))
        pubgRequestCounter++
        println("Amount of players to retrieve stats for: " + players.size)

        for (player in players) {
            player.rankedStats = seasonService.getRankedStats(player.id, currentSeason.seasonId)
            pubgRequestCounter++
            dbService.updateGuestRankAdr(player.name, player.rankedStats!!.adr, player.rankedStats!!.rank)
            dbService.updateSDRankAdr(player.name, player.rankedStats!!.adr, player.rankedStats!!.rank)
            if (pubgRequestCounter > 9) {
                println("Max request to pubg reached, waiting 1 min for next batch")
                Thread.sleep(60000)
                pubgRequestCounter = 0
            }
        }

        var users: List<User> = dbService.getAllGuestUsers()
        var embedBuilder: EmbedCreateSpec.Builder = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("HIGHSCORE")
        usersToEmbedField(embedBuilder, users)
        bot.sendMessage(embedBuilder.build(), ChannelId.GUEST_HIGHSCORE_CHANNEL, deleteLastMsg)

        users = dbService.getAllSDUsers()
        embedBuilder = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("HIGHSCORE")

        usersToEmbedField(embedBuilder, users)
        bot.sendMessage(embedBuilder.build(), ChannelId.SD_HIGHSCORE_CHANNEL, deleteLastMsg)
    }
}