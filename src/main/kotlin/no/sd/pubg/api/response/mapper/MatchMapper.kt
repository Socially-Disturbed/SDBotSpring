package no.sd.pubg.api.response.mapper

import no.sd.pubg.api.response.MatchesDataResponse
import no.sd.pubg.domain.MatchId

fun MatchesDataResponse.toMatchIds(): Set<MatchId> =
    data.map { MatchId(it.id) }.toSet()