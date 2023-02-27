package no.sd.pubg.api.cache

import no.sd.pubg.api.response.*
import no.sd.pubg.domain.MatchId
import no.sd.pubg.domain.PlayerId
import no.sd.pubg.domain.SeasonId

interface MatchCache {
    fun getMatch(matchId: MatchId): MatchResponseWrapper?
}

interface PlayerCache {
    fun getPlayersByNames(names: Set<String>, refresh: Boolean = false): Set<PlayerResponse>
    fun getPlayersByIds(ids: Set<String>, refresh: Boolean = false): Set<PlayerResponse>
}

interface SeasonCache {
    fun getAll(): Set<SeasonResponse>
    fun getCurrent(): SeasonResponse
    fun getRankedStats(playerId: PlayerId, seasonId: SeasonId): RankedPlayerStatsResponse
}