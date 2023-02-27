package no.sd.sdbot.discord.command

import discord4j.core.`object`.entity.Message

class CommandMessage(val message: Message) {

    var deleteCommandMsg: Boolean = false
    var deleteLastChannelMsg: Boolean = false
    var returningMsg: String? = null
    var returnMsgChannelId: String? = null

    fun getMethodName(): String {
        val msg = message.content
        val methodStartIndex: Int = if (msg.contains('!')) 1 else 0
        return if (msg.contains(" ")) msg.substring(methodStartIndex, msg.indexOf(" "))
        else msg.substring(methodStartIndex)
    }

    fun getArguments(): String {
        if (!message.content.contains(" ")) throw IllegalArgumentException("Missing arguments: No player name(s)")
        return message.content.substring(message.content.indexOf(" ") + 1)
    }
}