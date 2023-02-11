package no.sd.pubg.api.response.mapper

import no.sd.pubg.api.response.PlayerResponse
import no.sd.pubg.domain.Player
import no.sd.pubg.domain.PlayerId

fun PlayerResponse.toPlayer(): Player {
        return Player(
            PlayerId(id),
            attributes.name,
            relationships.matches.toMatchIds()
        )
}