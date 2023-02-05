package no.sd.bot

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SdBotApplication {

	companion object SdBotApplicationLogger {
		val logger = LoggerFactory.getLogger(this::class.simpleName)
	}


}

fun main(args: Array<String>) {
	runApplication<SdBotApplication>(*args)
}