package no.sd.sdbot.discord

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.`object`.audit.OptionKey.CHANNEL_ID
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.GuildMessageChannel
import discord4j.core.`object`.entity.channel.MessageChannel
import discord4j.core.spec.EmbedCreateSpec
import no.sd.sdbot.SdBotApplication.SdBotApplicationLogger.logger
import no.sd.sdbot.discord.command.CommandManager
import no.sd.sdbot.discord.command.CommandMessage
import org.springframework.stereotype.Component


@Component
class Bot (
    val gateway: GatewayDiscordClient,
    val commandManager: CommandManager
) {
    init {

        gateway
            .on(MessageCreateEvent::class.java)
            .map(MessageCreateEvent::getMessage)
            .filter { !it.author.get().isBot }
            .filter { it.content[0] == '!' || commandManager.isSpecialHandling(it) }
            .subscribe(
                { handleMessage(it) },
                { handleError(it) }
            )
    }

    fun handleMessage(message: Message) {

        if (message.content == "!join") {
            message.authorAsMember.block()!!.voiceState.block()!!.channel.block()!!.join().block()
        } else {
        val commandMessage = commandManager.handleCommand(CommandMessage(message))
        logger.info("Command from discord: ${message.content}")

        respondToCommand(commandMessage)
        }
    }

    fun sendMessage(msg: String, channelId: ChannelId, deleteLastMsgInChannel: Boolean) {
        val channel: MessageChannel? = gateway.getChannelById(Snowflake.of(channelId.id)).block() as MessageChannel?
        if (channel == null) {
            logger.error("Ingen channel med id: ${channelId.id}")
            return
        }
        if (deleteLastMsgInChannel) {
            channel.lastMessage.subscribe {
                it.delete().subscribe()
            }
        }
        channel.createMessage(msg).subscribe()
    }

    fun sendMessage(embedMsg: EmbedCreateSpec, channelId: ChannelId, deleteLastMsgInChannel: Boolean) {
        val channel: MessageChannel? = gateway.getChannelById(Snowflake.of(channelId.id)).block() as MessageChannel?
        if (channel == null) {
            logger.error("Ingen channel med id: ${channelId.id}")
            return
        }
        if (deleteLastMsgInChannel) {
            channel.lastMessage.subscribe {
                it.delete().subscribe()
            }
        }
        channel.createMessage(embedMsg).subscribe()
    }

    fun handleError(throwable: Throwable) {
        logger.error("Subscribe på MessageCreateEvent feilet: ${throwable.message}")
        throw RuntimeException("Subscribe feilet. ", throwable)
    }

    private fun respondToCommand(cmdMsg: CommandMessage) {
        with(cmdMsg) {
            if(deleteCommandMsg) this.message.delete().subscribe()

            if (returnMsgChannelIds.isEmpty()) { message.channel.subscribe { handleResponse(it, this) }}
            else {
                for (channel in returnMsgChannelIds) {
                    gateway
                        .getChannelById(Snowflake.of(channel))
                        .cast(MessageChannel::class.java)
                        .subscribe { handleResponse(it, this) }
                }
            }
        }
    }

    private fun handleResponse(channel: MessageChannel, cmdMsg: CommandMessage) {
        if (cmdMsg.sendEmbedMsg) {
            channel.createMessage(cmdMsg.embedMsg).subscribe()
        }
        if (cmdMsg.deleteLastChannelMsg) {
            channel.lastMessage.subscribe {
                it.delete().subscribe()
            }
        }
        cmdMsg.returningMsg?.let {
            channel.createMessage(cmdMsg.returningMsg).subscribe()
        }
    }
}