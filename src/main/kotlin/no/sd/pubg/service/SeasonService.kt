package no.sd.pubg.service

import no.sd.pubg.api.SeasonsApi
import no.sd.pubg.api.response.mapper.toRankedPlayerStats
import no.sd.pubg.domain.PlayerId
import no.sd.pubg.domain.RankedPlayerStats
import no.sd.pubg.domain.Season
import no.sd.pubg.domain.SeasonId
import org.springframework.stereotype.Service

@Service
class SeasonService(
        val seasonsApi: SeasonsApi
) {

    fun getCurrentSeason(): Season {
        return seasonsApi.getCurrentSeason()
    }

    fun getRankedStats(playerId: PlayerId, seasonId: SeasonId): RankedPlayerStats {
        return seasonsApi.getRankedStats(playerId, seasonId).toRankedPlayerStats()
    }
}