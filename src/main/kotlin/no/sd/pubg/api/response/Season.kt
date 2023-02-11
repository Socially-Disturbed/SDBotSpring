package no.sd.pubg.api.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SeasonsResponseWrapper (
    val data: List<SeasonResponse>,
    val links: SeasonLinks,
    val meta: Any?
)

data class SeasonResponse (
    val type: String,
    val id: String,
    val attributes: SeasonAttributesResponse
)

data class SeasonAttributesResponse (
    val isCurrentSeason: Boolean,
    val isOffSeason: Boolean
)

data class PlayerStatsResponse(
    val data: RankedPlayerStatsResponse,
    val links: SeasonLinks,
    val meta: Any?
)

data class RankedPlayerStatsResponse (
    val type: String,
    val attributes: RankedPlayerStatsAttributes,
    val relationships: SeasonRelationshipsResponse,
)

data class SeasonDataResponse (
    val data: SeasonIdResponse
)

data class SeasonIdResponse (
    val type: String,
    val id: String
)

data class RankedPlayerStatsAttributes(
    val rankedGameModeStats: RankedGameModeStatsResponse,
)

data class RankedGameModeStatsResponse (
    val squad: RankedGameModeStatsObjectResponse?,
    @JsonProperty("squad-fpp")
    val squadFpp: RankedGameModeStatsObjectResponse?
)

data class RankedGameModeStatsObjectResponse (
    val currentRankPoint: Int,
    val bestRankPoint: Int,
    val currentTier: TierResponse,
    val bestTier: TierResponse,
    val roundsPlayed: Int,
    val avgRank: Double,
    val top10Ratio: Double,
    val winRatio: Double,
    val assists: Int,
    val wins: Int,
    val kda: Double,
    val kills: Int,
    val deaths: Int,
    val damageDealt: Double,
    val dBNOs: Int,
)

data class TierResponse (
    val tier: String,
    val subTier: String
)

data class SeasonRelationshipsResponse (
    val player: PlayerDataResponse,
    val season: SeasonDataResponse
)

data class SeasonLinks(
    val self: String
)