package no.sd.sdbot.discord.command

import discord4j.core.`object`.entity.Message
import no.sd.sdbot.discord.function.SDFunctions
import no.sd.sdbot.discord.steinsakspapir.SSPType
import org.springframework.stereotype.Component

@Component
class CommandManager(
    val sdFunctions: SDFunctions
) {
    fun handleCommand(cmdMsg: CommandMessage): CommandMessage {
        val command = getCommandForSpecial(cmdMsg.getMethodName())
            ?: return cmdMsg.apply { returningMsg = "No such command: ${cmdMsg.getMethodName()}" }

        val sdFunction = command.getFunction(sdFunctions)
        return sdFunction.invoke(cmdMsg)
    }

    fun handleSpecialCommand(cmdMsg: CommandMessage): CommandMessage {
        val commandForSpecial = getCommandForSpecial(cmdMsg.message.content)!!
        val sdFunction = commandForSpecial.getFunction(sdFunctions)
        return sdFunction.invoke(cmdMsg)
    }

    fun getCommandForSpecial(methodName: String): Command? {
        return if (SSPType.values().any { it.name.lowercase() == methodName.lowercase() } ) Command.SteinSaksPapir
        else Command.getCommand(methodName)
    }

    fun isSpecialHandling(message: Message): Boolean {
        if (SSPType.getSSPType(message.content) == null) return false
        val channelType = message.channel.block()?.type ?: return false
        if (channelType.name != "DM") return false

        return true
    }
}