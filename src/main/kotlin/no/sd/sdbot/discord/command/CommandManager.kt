package no.sd.sdbot.discord.command

import discord4j.core.`object`.entity.Message
import no.sd.sdbot.discord.function.SDFunctions
import no.sd.sdbot.discord.steinsakspapir.SSPType
import org.springframework.stereotype.Component

@Component
class CommandManager(
    val sdFunctions: SDFunctions
) {
    fun handleCommand(cmdMsg: CommandMessage): CommandMessage? {
        // finn command hvis den finnes, eller returner commandMessage med "no such command"
        val command = getCommand(cmdMsg.getMethodName())
            ?: return cmdMsg.apply { returningMsg = "No such command: ${cmdMsg.getMethodName()}" }

        val sdFunction = command.getFunction(sdFunctions)
        return try {
            sdFunction.invoke(cmdMsg)
        } catch (ex: Exception){
            cmdMsg.apply { returningMsg = ex.message }
        }
    }

    fun getCommand(methodName: String): Command? {
        return if (SSPType.values().any { it.name.lowercase() == methodName.lowercase() } ) Command.SteinSaksPapir
        else Command.getCommand(methodName)
    }

    // legg til eventuell spesialhåndtering av forskjellige kommandoer
    fun isSpecialHandling(message: Message): Boolean {
        return when {
            checkIfSteinSaksPapir(message) -> true
            else -> false
        }
    }

    fun checkIfSteinSaksPapir(message: Message): Boolean {
        if (SSPType.getSSPType(message.content) == null) return false
        val channelType = message.channel.block()?.type ?: return false
        if (channelType.name != "DM") return false

        return true
    }
}