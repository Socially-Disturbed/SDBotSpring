package no.sd.pubg.api.webclient

import org.springframework.web.reactive.function.client.WebClient

interface PubgApiClient {
    fun doApiRequest(request: String): WebClient.ResponseSpec
}