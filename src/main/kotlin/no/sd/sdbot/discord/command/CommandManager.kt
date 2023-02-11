package no.sd.sdbot.discord.command

import no.sd.sdbot.discord.function.SDFunctions
import org.springframework.stereotype.Component

@Component
class CommandManager(
    val sdFunctions: SDFunctions
) {
    fun handleCommand(commandMessage: CommandMessage): CommandMessage {
        val command = Command.getCommand(commandMessage.getMethodName())
            ?: return commandMessage.apply { returningMsg = "No such command: ${commandMessage.getMethodName()}" }

        val sdFunction = getFunction(sdFunctions, command)
            ?: return commandMessage.apply { returningMsg = "No function for command: ${commandMessage.getMethodName()}" }

        return sdFunction(commandMessage)
    }
}