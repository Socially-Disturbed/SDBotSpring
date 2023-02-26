package no.sd.pubg.api

import no.sd.pubg.api.response.*
import no.sd.pubg.api.response.mapper.toSeason
import no.sd.pubg.api.webclient.PubgApiClient
import no.sd.pubg.domain.PlayerId
import no.sd.pubg.domain.Season
import no.sd.pubg.domain.SeasonId
import org.springframework.stereotype.Service

@Service
class SeasonsApi (private val apiClient: PubgApiClient) {

    companion object ApiResources {
        private const val seasonsEndpoint = "/seasons"
        private const val rankedStatsEndpoint = "/players/%s/seasons/%s/ranked"
    }

    fun getAvailableSeasons(): Set<SeasonResponse> {
        val seasonsResponse = apiClient.doApiRequest(seasonsEndpoint)
            .bodyToMono(SeasonsResponseWrapper::class.java)
            .block()

        return seasonsResponse?.data!!.toSet()
    }

    fun getCurrentSeason(): Season {
        return getAvailableSeasons().first { it.attributes.isCurrentSeason }.toSeason()
    }

    fun getRankedStats(playerId: PlayerId, seasonId: SeasonId): RankedPlayerStatsResponse {
        val request = String.format(rankedStatsEndpoint, playerId.id, seasonId.id)
        val rankedPlayerStatsResponse = apiClient.doApiRequest(request)
            .bodyToMono(PlayerStatsResponse::class.java)
            .block()

        return rankedPlayerStatsResponse!!.data
    }
}