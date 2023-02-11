package no.sd.pubg.api.cache

import no.sd.pubg.api.PlayersApi
import no.sd.pubg.api.response.PlayerResponse
import org.springframework.stereotype.Component

@Component
class InMemoryPlayerCache(
    val playersApi: PlayersApi
): PlayerCache {

    private val cache: MutableSet<PlayerResponse> = HashSet()

    override fun getPlayersByNames(names: Set<String>, refresh: Boolean): Set<PlayerResponse> {
        if (refresh) return getPlayersFromApi(names, playersApi::getPlayersByNames)

        val notInCache = names.filter { name -> cache.none { name == it.attributes.name } }.toSet()
        val fetchedPlayerResponses = getPlayersFromApi(notInCache, playersApi::getPlayersByNames)

        val existingPlayers = cache.filter { names.contains(it.attributes.name) }.toSet()

        return fetchedPlayerResponses + existingPlayers
    }

    override fun getPlayersByIds(ids: Set<String>, refresh: Boolean): Set<PlayerResponse> {
        if (refresh) return getPlayersFromApi(ids, playersApi::getPlayersByIds)

        val notInCache = ids.filter { id -> cache.none { id == it.id } }.toSet()
        val fetchedPlayers = getPlayersFromApi(notInCache, playersApi::getPlayersByIds)

        val existingPlayers = cache.filter { ids.contains(it.id) }.toSet()

        return fetchedPlayers + existingPlayers
    }

    private fun getPlayersFromApi(identifiers: Set<String>, apiFunc: (String) -> Set<PlayerResponse>): Set<PlayerResponse> {
        val fetchedPlayers = apiFunc(identifiers.joinToString(","))
        if (fetchedPlayers.isNotEmpty()) cache.addAll(fetchedPlayers)
        return fetchedPlayers
    }
}