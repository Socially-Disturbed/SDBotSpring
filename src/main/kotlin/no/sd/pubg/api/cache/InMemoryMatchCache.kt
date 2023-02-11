package no.sd.pubg.api.cache

import no.sd.pubg.api.MatchesApi
import no.sd.pubg.api.response.MatchResponseWrapper
import no.sd.pubg.domain.MatchId
import org.springframework.stereotype.Component

@Component
class InMemoryMatchCache(
    val matchesApi: MatchesApi
): MatchCache {

    private val cache: MutableMap<MatchId, MatchResponseWrapper> = HashMap()

    override fun getMatch(matchId: MatchId): MatchResponseWrapper =
        cache.getOrPut(matchId) { matchesApi.getMatch(matchId.id) }
}