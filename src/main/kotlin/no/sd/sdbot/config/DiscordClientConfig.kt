package no.sd.sdbot.config

import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.`object`.entity.User
import no.sd.sdbot.SdBotApplication.SdBotApplicationLogger.logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class DiscordClientConfig (private val environment: Environment) {

    @Bean
    fun createDiscordClient() : GatewayDiscordClient {
        val discordClient = DiscordClient.create(environment.getRequiredProperty("discordToken"))
        val gateway = discordClient.login().block()!!
        gateway.eventDispatcher
            .on(ReadyEvent::class.java)
            .subscribe(
                {
                    val user: User = it.self
                    logger.info("Logged in as ${user.username} ${user.discriminator}")
                },
                { throw RuntimeException("Feil i oppstart", it) }
        )
        return gateway
    }
}