package no.sd.pubg.config

import no.sd.pubg.api.webclient.PubgApiClient
import no.sd.pubg.api.webclient.PubgRestClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class ApiClientConfig(
    private val environment: Environment
) {

    companion object PubgApiBaseUrl {
        private const val baseUrl = "https://api.pubg.com/shards/steam"
    }

    @Bean
    fun pubgApiClient(): PubgApiClient {
        return PubgRestClient(createWebClient())
    }

    fun createWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.api+json")
            .defaultHeader(HttpHeaders.AUTHORIZATION, environment.getProperty("pubg.apiKey", "noToken"))
            .build()
    }
}