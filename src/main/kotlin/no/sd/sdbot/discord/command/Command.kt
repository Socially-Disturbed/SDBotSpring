package no.sd.sdbot.discord.command

import no.sd.sdbot.discord.command.Command.*
import no.sd.sdbot.discord.function.SDFunctions
import java.util.*

enum class Command {
    GetPlayersByNames,
    GetPlayersByIds,
    GetMatch,
    GetLastMatch,
    UpdateGuestScore,
    UpdateGuestWin,
    UpdateSDScore,
    UpdateSDWin,
    GetGuestScoreBoard,
    GetSDScoreBoard,
    Help;

    companion object {
        fun getCommand(commandString: String): Command? {
            return try {
                values().first { it.name.lowercase() == commandString.lowercase() }
            } catch (iaex: NoSuchElementException) {
                null
            }
        }
    }
}

fun getFunction(sdFunctions: SDFunctions, command: Command): ((CommandMessage) -> CommandMessage)? {

    val commandToFunctionMap: MutableMap<Command, (CommandMessage) -> CommandMessage> = EnumMap(Command::class.java)

    commandToFunctionMap[GetPlayersByNames] = sdFunctions::getPlayersByNames
    commandToFunctionMap[GetPlayersByIds] = sdFunctions::getPlayersById
    commandToFunctionMap[GetMatch] = sdFunctions::getMatch
    commandToFunctionMap[GetLastMatch] = sdFunctions::getLastMatch
    commandToFunctionMap[UpdateGuestScore] = sdFunctions::updateGuestScore
    commandToFunctionMap[UpdateGuestWin] = sdFunctions::updateGuestWin
    commandToFunctionMap[UpdateSDScore] = sdFunctions::updateSDScore
    commandToFunctionMap[UpdateSDWin] = sdFunctions::updateSDWin
    commandToFunctionMap[GetGuestScoreBoard] = sdFunctions::getGuestScoreBoard
    commandToFunctionMap[GetSDScoreBoard] = sdFunctions::getSDScoreBoard
    commandToFunctionMap[Help] = sdFunctions::help

    return commandToFunctionMap[command]
}
