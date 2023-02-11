package no.sd.pubg.api

import no.sd.pubg.api.response.PlayerResponse
import no.sd.pubg.api.response.PlayersResponseWrapper
import no.sd.pubg.api.webclient.PubgApiClient
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec

@Service
class PlayersApi (private val apiClient: PubgApiClient) {

    companion object ApiResources {
        private const val playersEndpoint = "/players"

        private enum class Filters(val value: String) {
            NAMES("?filter[playerNames]="),
            IDS("?filter[playerIds]=")
        }
    }

    fun getPlayersByNames(playerNames: String): Set<PlayerResponse> {
        val responseSpec = apiClient.doApiRequest(playersEndpoint + Filters.NAMES.value + playerNames)
        return getResponseWrapper(responseSpec)?.data?.toSet() ?: emptySet()
    }

    fun getPlayersByIds(playerIds: String): Set<PlayerResponse> {
        val responseSpec = apiClient.doApiRequest(playersEndpoint + Filters.IDS + playerIds)
        return getResponseWrapper(responseSpec)?.data?.toSet() ?: emptySet()
    }

    private fun getResponseWrapper(responseSpec: ResponseSpec): PlayersResponseWrapper? {
        return try {
            responseSpec
                .onStatus(
                    { httpStatus -> httpStatus.value() == 404 },
                    { response -> throw RuntimeException(response.statusCode().toString()) }
                )
                .bodyToMono(PlayersResponseWrapper::class.java)
                .block()
        } catch (ignored: RuntimeException) {
            null
        }
    }
}