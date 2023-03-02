package no.sd.sdbot.discord.function

import no.sd.sdbot.discord.command.CommandMessage

interface SDFunctions {
    fun getPlayersByNames(cmdMsg: CommandMessage): CommandMessage
    fun getPlayersById(cmdMsg: CommandMessage): CommandMessage
    fun getMatch(cmdMsg: CommandMessage): CommandMessage
    fun getLastMatch(cmdMsg: CommandMessage): CommandMessage
    fun updateGuestScore(cmdMsg: CommandMessage): CommandMessage
    fun updateGuestWin(cmdMsg: CommandMessage): CommandMessage
    fun updateSDScore(cmdMsg: CommandMessage): CommandMessage
    fun updateSDWin(cmdMsg: CommandMessage): CommandMessage
    fun getGuestScoreBoard(cmdMsg: CommandMessage): CommandMessage
    fun getSDScoreBoard(cmdMsg: CommandMessage): CommandMessage
    fun help(cmdMsg: CommandMessage): CommandMessage
    fun steinSaksPapir(cmdMsg: CommandMessage): CommandMessage
    fun test(cmdMsg: CommandMessage): CommandMessage
}

