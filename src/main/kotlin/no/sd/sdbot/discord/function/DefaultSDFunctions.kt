package no.sd.sdbot.discord.function

import no.sd.pubg.domain.Player
import no.sd.pubg.domain.print.prettyPrint
import no.sd.pubg.service.PubgService
import no.sd.pubg.service.prettyPrint
import no.sd.sdbot.db.DbService
import no.sd.sdbot.discord.ChannelId
import no.sd.sdbot.discord.command.CommandMessage
import no.sd.sdbot.discord.steinsakspapir.SSPHandler
import no.sd.sdbot.discord.utility.print.prettyPrint
import org.springframework.stereotype.Component

@Component
class DefaultSDFunctions(
    val pubgService: PubgService,
    val dbService: DbService,
    val sspHandler: SSPHandler
): SDFunctions {

    override fun getPlayersByNames(cmdMsg: CommandMessage): CommandMessage {
        val playerNames: String = cmdMsg.getArguments()

        val players: Set<Player> = pubgService.getPlayersByNames(playerNames)
        if (players.isEmpty()) { return cmdMsg.apply { returningMsg = "No players with name: $playerNames" }}

        return cmdMsg.apply {
            returningMsg = players.joinToString { it.prettyPrint() }
            deleteCommandMsg = true
        }
    }

    override fun getPlayersById(cmdMsg: CommandMessage): CommandMessage {
        val playerIds: String = cmdMsg.getArguments()
        if (playerIds == "12345") cmdMsg.returningMsg = "Dy7t" else cmdMsg.returningMsg = "No PlayerResponse with id: $playerIds"
        return cmdMsg
    }

    override fun getMatch(cmdMsg: CommandMessage): CommandMessage {
        val matchId: String = cmdMsg.getArguments()
        if (matchId == "9999") cmdMsg.returningMsg = "MatchResponse #!FA!#FA" else cmdMsg.returningMsg = "No MatchResponse with id: $matchId"
        return cmdMsg
    }

    override fun getLastMatch(cmdMsg: CommandMessage): CommandMessage {

        val arguments = cmdMsg.getArguments().split(",")
        if (arguments.size != 1) throw RuntimeException("Get last match requires only 1 player name")

        val playerByName = pubgService.getPlayersByNames(arguments[0]).first()
        val lastMatchId = playerByName.recentMatchIds.first()

        val lastMatch = pubgService.getMatchById(lastMatchId)

        val statsForPlayer = lastMatch.included.participants
            .map { participantResponse -> participantResponse.attributes.stats }
            .first { participantStatsResponse -> participantStatsResponse.name.equals(playerByName) }

        cmdMsg.returningMsg = statsForPlayer.prettyPrint()
        return cmdMsg
    }

    override fun updateGuestScore(cmdMsg: CommandMessage): CommandMessage {
        val arrOfMsg = getArgumentsFromCmdMsg(cmdMsg)

        val name = arrOfMsg[0]
        val score = arrOfMsg[1].toDouble()

        dbService.updateGuestScore(name, score)

        return getGuestScoreBoard(cmdMsg)
    }

    fun getArgumentsFromCmdMsg(cmdMsg: CommandMessage): Array<String> {
        val updateInfo: String? = cmdMsg.getArguments()
        val arrOfMsg = updateInfo!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        println(arrOfMsg)

        if (arrOfMsg.size != 2) {
            throw IllegalArgumentException("Wrong amount of arguments, should be 3: $arrOfMsg")
        }

        return arrOfMsg
    }

    override fun updateGuestWin(cmdMsg: CommandMessage): CommandMessage {
        val name: String = cmdMsg.getArguments()
        dbService.updateGuestWin(name)

        return getGuestScoreBoard(cmdMsg)
    }

    override fun updateSDScore(cmdMsg: CommandMessage): CommandMessage {
        val arrOfMsg = getArgumentsFromCmdMsg(cmdMsg)
        val name = arrOfMsg[0]
        val score = arrOfMsg[1].toDouble()

        dbService.updateSDScore(name, score)

        return getSDScoreBoard(cmdMsg)
    }

    override fun updateSDWin(cmdMsg: CommandMessage): CommandMessage {
        val name: String = cmdMsg.getArguments()
        dbService.updateSDWin(name)

        return getSDScoreBoard(cmdMsg)
    }

    override fun getGuestScoreBoard(cmdMsg: CommandMessage): CommandMessage {
        return cmdMsg.apply {
            returningMsg = dbService.getAllGuestUsers().joinToString { it.prettyPrint() }
            returnMsgChannelId = ChannelId.GUEST_HIGHSCORE_CHANNEL.id
        }
    }

    override fun getSDScoreBoard(cmdMsg: CommandMessage): CommandMessage {
        return cmdMsg.apply {
            returningMsg = dbService.getAllSDUsers().joinToString { it.prettyPrint() }
            returnMsgChannelId = ChannelId.SD_HIGHSCORE_CHANNEL.id
        }
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
        return cmdMsg.apply {
            returningMsg = dbService.getAllSDUsers().joinToString { it.prettyPrint() }
        }
    }

    override fun joinChannel(cmdMsg: CommandMessage): CommandMessage? {
        cmdMsg.message
            .authorAsMember.block()!!
            .voiceState.block()!!
            .channel.block()!!
            .join().block()

        return null
    }
}