package no.sd.pubg.api.response.mapper

import no.sd.pubg.api.response.RankedGameModeStatsObjectResponse
import no.sd.pubg.api.response.RankedPlayerStatsResponse
import no.sd.pubg.domain.RankedPlayerStats

fun RankedPlayerStatsResponse.toRankedPlayerStats(): RankedPlayerStats {
    val squadFpp: RankedGameModeStatsObjectResponse? = attributes.rankedGameModeStats.squadFpp
    return RankedPlayerStats(
            (squadFpp!!.damageDealt/squadFpp.roundsPlayed).toInt(),
            squadFpp.currentTier.tier + " " + squadFpp.currentTier.subTier
    )
}