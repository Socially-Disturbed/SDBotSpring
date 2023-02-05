package no.sd.bot.discord

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.`object`.entity.Message
import no.sd.bot.SdBotApplication.SdBotApplicationLogger.logger
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class Bot (gateway: GatewayDiscordClient) {
    init {
        gateway
            .on(MessageCreateEvent::class.java)
            .map(MessageCreateEvent::getMessage)
            .subscribe(
                { handleMessage(it) },
                { handleError(it) }
            )
    }

    fun handleMessage(message: Message) {
        val messageString = message.content
        logger.info(messageString)
    }

    fun handleError(throwable: Throwable) {
        throw RuntimeException("fydda")
    }
}