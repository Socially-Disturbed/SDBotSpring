package no.sd.sdbot.discord.command

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.User
import discord4j.core.spec.EmbedCreateSpec

class CommandMessage(val message: Message) {

    var deleteCommandMsg: Boolean = false
    var deleteLastChannelMsg: Boolean = false
    var returningMsg: String? = null
    var returnMsgChannelIds: ArrayList<String> = ArrayList()
    var sendEmbedMsg: Boolean = false
    var embedMsg: EmbedCreateSpec? = null
    var user: User? = null

    fun getMethodName(): String {
        val msg = message.content
        val methodStartIndex: Int = if (msg.contains('!')) 1 else 0
        return if (msg.contains(" ")) msg.substring(methodStartIndex, msg.indexOf(" "))
        else msg.substring(methodStartIndex)
    }

    fun getArguments(): String? {
        return if (!message.content.contains(" ")) null
        else message.content.substring(message.content.indexOf(" ") + 1)
    }
}