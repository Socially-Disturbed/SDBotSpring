package no.sd.pubg.api.webclient

import org.springframework.web.reactive.function.client.WebClient

class PubgRestClient (
    private val webClient: WebClient
): PubgApiClient {

    override fun doApiRequest(request: String): WebClient.ResponseSpec {
        return webClient.get()
            .uri(request)
            .retrieve()
    }
}