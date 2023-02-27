package no.sd.pubg.service

import no.sd.pubg.api.MatchesApi
import no.sd.pubg.api.PlayersApi
import no.sd.pubg.api.SeasonsApi
import no.sd.pubg.api.response.MatchResponseWrapper
import no.sd.pubg.api.response.mapper.toPlayer
import no.sd.pubg.api.response.mapper.toRankedPlayerStats
import no.sd.pubg.domain.*
import org.springframework.stereotype.Service

@Service
class PubgService(
    val playersApi: PlayersApi,
    val matchesApi: MatchesApi,
    val seasonsApi: SeasonsApi
) {
    fun getPlayersByNames(playerNames: String): Set<Player> {
        val playersByNames = playersApi.getPlayersByNames(playerNames)
        if (playersByNames.isEmpty()) throw RuntimeException("No such player(s): $playerNames")
        return playersByNames
            .map { it.toPlayer() }
            .toSet()
    }

    fun getMatchById(matchId: MatchId): MatchResponseWrapper {
        return matchesApi.getMatch(matchId = matchId.id)
            ?: throw RuntimeException("Match not found: ${matchId.id}")
    }

    fun getCurrentSeason(): Season {
        return seasonsApi.getCurrentSeason()
    }

    fun getRankedStats(playerId: PlayerId, seasonId: SeasonId): RankedPlayerStats {
        return seasonsApi.getRankedStats(playerId, seasonId).toRankedPlayerStats()
    }
}