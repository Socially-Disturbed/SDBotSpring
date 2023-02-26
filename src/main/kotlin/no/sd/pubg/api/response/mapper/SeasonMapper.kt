package no.sd.pubg.api.response.mapper

import no.sd.pubg.api.response.SeasonResponse
import no.sd.pubg.domain.Season
import no.sd.pubg.domain.SeasonId

fun SeasonResponse.toSeason(): Season {
    return Season(
            SeasonId(id),
    )
}