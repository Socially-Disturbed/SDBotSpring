package no.sd.sdbot.discord

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.channel.MessageChannel
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

        this.gateway
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
        val commandMessage = commandManager.handleCommand(CommandMessage(message))
        logger.info("Command from discord: ${message.content}")

        respondToCommand(commandMessage)
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

    fun handleError(throwable: Throwable) {
        logger.error("Subscribe på MessageCreateEvent feilet: ${throwable.message}")
        throw RuntimeException("Subscribe feilet. ", throwable)
    }

    private fun respondToCommand(cmdMsg: CommandMessage?) {
        if (cmdMsg != null) {
            with(cmdMsg) {
                if (deleteCommandMsg) this.message.delete().subscribe()

                if (returnMsgChannelId == null) {
                    message.channel.subscribe { handleResponse(it, this) }
                } else {
                    gateway
                        .getChannelById(Snowflake.of(returnMsgChannelId!!))
                        .cast(MessageChannel::class.java)
                        .subscribe { handleResponse(it, this) }
                }
            }
        }
    }

    private fun handleResponse(channel: MessageChannel, cmdMsg: CommandMessage) {
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