package no.sd.pubg.api.cache

import no.sd.pubg.api.SeasonsApi
import no.sd.pubg.api.response.RankedPlayerStatsResponse
import no.sd.pubg.api.response.SeasonResponse
import no.sd.pubg.domain.PlayerId
import no.sd.pubg.domain.SeasonId
import org.springframework.stereotype.Component

@Component
class InMemorySeasonCache(
    val seasonsApi: SeasonsApi
): SeasonCache {

    private val seasonsCache: MutableSet<SeasonResponse> = HashSet()
    private val rankedStatsCache: MutableMap<PlayerId, MutableMap<SeasonId, RankedPlayerStatsResponse>> = HashMap()

    override fun getAll(): Set<SeasonResponse> {
        if (seasonsCache.isEmpty()) {
            val availableSeasons = seasonsApi.getAvailableSeasons()
            seasonsCache.addAll(availableSeasons)
        }
        return seasonsCache
    }

    override fun getCurrent(): SeasonResponse = getAll().first { it.attributes.isCurrentSeason }

    override fun getRankedStats(playerId: PlayerId, seasonId: SeasonId): RankedPlayerStatsResponse {
        val seasonsForPlayerMap = rankedStatsCache[playerId]

        return if (seasonsForPlayerMap == null || seasonsForPlayerMap[seasonId] == null) {
            val rankedStats = seasonsApi.getRankedStats(playerId, seasonId)
            rankedStatsCache.computeIfAbsent(playerId) { HashMap() }
            rankedStatsCache[playerId]!![seasonId] = rankedStats
            rankedStats
        } else seasonsForPlayerMap[seasonId]!!
    }
}