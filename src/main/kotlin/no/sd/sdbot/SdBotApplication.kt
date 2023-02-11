package no.sd.sdbot

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["no.sd.sdbot", "no.sd.pubg"])
class SdBotApplication {

	companion object SdBotApplicationLogger {
		val logger = LoggerFactory.getLogger(this::class.simpleName)
	}
}

fun main(args: Array<String>) {
	runApplication<SdBotApplication>(*args)
}