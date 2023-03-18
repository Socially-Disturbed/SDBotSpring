package no.sd.sdbot.discord.function

import discord4j.core.spec.EmbedCreateSpec
import discord4j.rest.util.Color
import no.sd.pubg.domain.Player
import no.sd.pubg.domain.print.prettyPrint
import no.sd.pubg.service.MatchService
import no.sd.pubg.service.PlayerService
import no.sd.sdbot.db.DbService
import no.sd.sdbot.db.User
import no.sd.sdbot.discord.ChannelId
import no.sd.sdbot.discord.command.CommandMessage
import no.sd.sdbot.discord.steinsakspapir.SSPHandler
import no.sd.sdbot.discord.utility.print.userListToPrint
import no.sd.sdbot.discord.utility.usersToEmbedField
import org.springframework.stereotype.Component
import java.time.Instant


@Component
class DefaultSDFunctions(
    val playerService: PlayerService,
    val matchService: MatchService,
    val dbService: DbService,
    val sspHandler: SSPHandler
): SDFunctions {

    override fun getPlayersByNames(cmdMsg: CommandMessage): CommandMessage {
        cmdMsg.deleteCommandMsg = true

        val playerNames: String = cmdMsg.getArguments()
            ?: return cmdMsg.apply { returningMsg = "No provided arguments" }

        val players: Set<Player> = playerService.getPlayersByNames(playerNames)
        if (players.isEmpty()) { return cmdMsg.apply { returningMsg = "No players with name: $playerNames" }}

        return cmdMsg.apply { returningMsg = players.joinToString { it.prettyPrint() } }
    }

    override fun getPlayersById(cmdMsg: CommandMessage): CommandMessage {
        val playerIds: String? = cmdMsg.getArguments()
        if (playerIds == "12345") cmdMsg.returningMsg = "Dy7t" else cmdMsg.returningMsg = "No PlayerResponse with id: $playerIds"
        return cmdMsg
    }

    override fun getMatch(cmdMsg: CommandMessage): CommandMessage {
        val matchId: String? = cmdMsg.getArguments()
        if (matchId == "9999") cmdMsg.returningMsg = "MatchResponse #!FA!#FA" else cmdMsg.returningMsg = "No MatchResponse with id: $matchId"
        return cmdMsg
    }

    override fun getLastMatch(cmdMsg: CommandMessage): CommandMessage {
        TODO()
//        val playerName: String? = cmdMsg.getCommandArguments()
//        val playersByName: Set<PlayerResponse> = playersApi.getPlayersByName(playerName)
//        if (playersByName.isEmpty()) cmdMsg.returningMsg = "No players with name: $playerName")
//        val player: PlayerResponse = playersByName.iterator().next()
//        val matchId: MatchIdResponse = player.getLastMatches().get(0)
//        val match: MatchResponse = matchesApi.getMatch(matchId)
//        val matchForPlayer: ParticipantResponse = match.ParticipantResponses.stream()
//            .filter { ParticipantResponse -> player.getPlayerName().equals(ParticipantResponse.player.getPlayerName()) }
//            .toList()
//            .get(0)
//        cmdMsg.returningMsg = matchForPlayer.toString())
//        return cmdMsg
    }

    override fun updateGuestScore(cmdMsg: CommandMessage): CommandMessage {
        val updateInfo: String? = cmdMsg.getArguments()
        val arrOfMsg = updateInfo!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        println(arrOfMsg)
        if (arrOfMsg.size != 2) {
            cmdMsg.returningMsg = "Wrong amount of arguments, should be 3: $arrOfMsg"
            return cmdMsg
        }
        val player = arrOfMsg[0]
        val score = arrOfMsg[1].toFloat()
        dbService.updateGuestScore(player, score)
        val users = dbService.getAllGuestUsers()
        cmdMsg.sendEmbedMsg = true
        cmdMsg.deleteLastChannelMsg = true
        cmdMsg.deleteCommandMsg = true
        cmdMsg.returnMsgChannelIds.add(ChannelId.GUEST_HIGHSCORE_CHANNEL.id)

        val embedBuilder: EmbedCreateSpec.Builder = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("HIGHSCORE")

        usersToEmbedField(embedBuilder, users)
        cmdMsg.embedMsg = embedBuilder.build()
        return cmdMsg
    }

    override fun updateGuestWin(cmdMsg: CommandMessage): CommandMessage {
        val playerName: String? = cmdMsg.getArguments()
        dbService.updateGuestWin(playerName)
        val users = dbService.getAllGuestUsers()
        cmdMsg.sendEmbedMsg = true
        cmdMsg.deleteLastChannelMsg = true
        cmdMsg.deleteCommandMsg = true
        cmdMsg.returnMsgChannelIds.add(ChannelId.GUEST_HIGHSCORE_CHANNEL.id)

        val embedBuilder: EmbedCreateSpec.Builder = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("HIGHSCORE")

        usersToEmbedField(embedBuilder, users)
        cmdMsg.embedMsg = embedBuilder.build()
        return cmdMsg
    }

    override fun updateSDScore(cmdMsg: CommandMessage): CommandMessage {
        val updateInfo: String? = cmdMsg.getArguments()
        val arrOfMsg = updateInfo!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        println(arrOfMsg)
        if (arrOfMsg.size != 2) {
            cmdMsg.returningMsg = "Wrong amount of arguments, should be 3: $arrOfMsg"
            return cmdMsg
        }
        val player = arrOfMsg[0]
        val score = arrOfMsg[1].toFloat()
        dbService.updateSDScore(player, score)
        val users = dbService.getAllSDUsers()
        cmdMsg.sendEmbedMsg = true
        cmdMsg.deleteLastChannelMsg = true
        cmdMsg.deleteCommandMsg = true
        cmdMsg.returnMsgChannelIds.add(ChannelId.SD_HIGHSCORE_CHANNEL.id)

        val embedBuilder: EmbedCreateSpec.Builder = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("HIGHSCORE")

        usersToEmbedField(embedBuilder, users)
        cmdMsg.embedMsg = embedBuilder.build()
        return cmdMsg
    }

    override fun updateSDWin(cmdMsg: CommandMessage): CommandMessage {
        val playerName: String? = cmdMsg.getArguments()
        dbService.updateSDWin(playerName)
        val users = dbService.getAllSDUsers()
        cmdMsg.sendEmbedMsg = true
        cmdMsg.deleteLastChannelMsg = true
        cmdMsg.deleteCommandMsg = true
        cmdMsg.returnMsgChannelIds.add(ChannelId.SD_HIGHSCORE_CHANNEL.id)

        val embedBuilder: EmbedCreateSpec.Builder = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("HIGHSCORE")

        usersToEmbedField(embedBuilder, users)
        cmdMsg.embedMsg = embedBuilder.build()
        return cmdMsg
    }

    override fun getGuestScoreBoard(cmdMsg: CommandMessage): CommandMessage {
        val users: List<User> = dbService.getAllGuestUsers()
        val embedBuilder: EmbedCreateSpec.Builder = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("HIGHSCORE")

        usersToEmbedField(embedBuilder, users)
        cmdMsg.embedMsg = embedBuilder.build()
        cmdMsg.sendEmbedMsg = true
        return cmdMsg
    }

    override fun getSDScoreBoard(cmdMsg: CommandMessage): CommandMessage {
        val users: List<User> = dbService.getAllSDUsers()
        val embedBuilder: EmbedCreateSpec.Builder = EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .title("HIGHSCORE")

        usersToEmbedField(embedBuilder, users)
        cmdMsg.embedMsg = embedBuilder.build()
        cmdMsg.sendEmbedMsg = true
        return cmdMsg
    }

    override fun help(cmdMsg: CommandMessage): CommandMessage {
        TODO()
//        cmdMsg.returningMsg = Utilities.commands)
//        return cmdMsg
    }

    override fun steinSaksPapir(cmdMsg: CommandMessage): CommandMessage {
        return sspHandler.startOrAddSSPRundeEntry(cmdMsg)
    }

    override fun test(cmdMsg: CommandMessage): CommandMessage {
        val user = cmdMsg.message.author.get()
        cmdMsg.returnMsgChannelIds.add(user!!.id.asString())
        cmdMsg.returningMsg = "du er med i steinsakspapir"
        return cmdMsg
    }
}