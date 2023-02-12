package no.sd.sdbot.discord.command

import no.sd.sdbot.discord.function.SDFunctions
import no.sd.sdbot.discord.steinsakspapir.SSPType
import org.springframework.stereotype.Component

@Component
class CommandManager(
    val sdFunctions: SDFunctions
) {
    fun handleCommand(cmdMsg: CommandMessage): CommandMessage {
        val command = checkSpecialHandling(cmdMsg.getMethodName())
            ?: return cmdMsg.apply { returningMsg = "No such command: ${cmdMsg.getMethodName()}" }

        val sdFunction = command.getFunction(sdFunctions)
        return sdFunction.invoke(cmdMsg)
    }

    fun checkSpecialHandling(methodName: String): Command? {
        return if (SSPType.values().any { it.name.lowercase() == methodName.lowercase() } ) Command.SteinSaksPapir
        else Command.getCommand(methodName)
    }
}