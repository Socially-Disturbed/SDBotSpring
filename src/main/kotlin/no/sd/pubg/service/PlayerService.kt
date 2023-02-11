package no.sd.pubg.service

import no.sd.pubg.api.PlayersApi
import no.sd.pubg.api.response.mapper.toPlayer
import no.sd.pubg.domain.Player
import org.springframework.stereotype.Service

@Service
class PlayerService(
    val playersApi: PlayersApi
) {
    fun getPlayersByNames(playerNames: String): Set<Player> {
        return playersApi.getPlayersByNames(playerNames)
            .map { it.toPlayer() }
            .toSet()
    }
}