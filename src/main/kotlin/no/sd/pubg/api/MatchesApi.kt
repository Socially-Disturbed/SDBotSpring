package no.sd.pubg.api

import no.sd.pubg.api.response.MatchResponseWrapper
import no.sd.pubg.api.webclient.PubgApiClient
import org.springframework.stereotype.Service

@Service
class MatchesApi (private val apiClient: PubgApiClient) {

    companion object ApiResources {
        private const val matchesEndpoint = "/matches/"
    }

    fun getMatch(matchId: String): MatchResponseWrapper? {
        val responseSpec = apiClient.doApiRequest(matchesEndpoint + matchId)

        return responseSpec
            .bodyToMono(MatchResponseWrapper::class.java)
            .block()
    }
}