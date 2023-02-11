package no.sd.sdbot.discord.command

import discord4j.core.`object`.entity.Message

class CommandMessage(val message: Message) {

    var deleteCommandMsg: Boolean = false
    var deleteLastChannelMsg: Boolean = false
    var returningMsg: String? = null
    var returnMsgChannelId: String? = null

    fun getMethodName(): String {
        val msg = message.content
        return if (msg.contains(" ")) msg.substring(1, msg.indexOf(" "))
        else msg.substring(1)
    }

    fun getArguments(): String? {
        return if (!message.content.contains(" ")) null
        else message.content.substring(message.content.indexOf(" ") + 1)
    }
}