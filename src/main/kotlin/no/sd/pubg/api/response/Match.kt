package no.sd.pubg.api.response

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import no.sd.pubg.api.response.deserializer.MatchIncludedDeserializer

data class MatchResponseWrapper (
    val data: MatchResponse,
    @JsonDeserialize(using = MatchIncludedDeserializer::class)
    val included: IncludedResponse,
    val links: MatchLinksResponse,
    val meta: Any?
)

data class MatchResponse (
    val type: String,
    val id: String,
    val attributes: MatchAttributesResponse,
    val relationships: MatchRelationshipsResponse,
    val links: MatchLinksResponse,
)

data class MatchesDataResponse (
    val data: List<MatchIdResponse>
)

data class MatchIdResponse (
    val id: String,
    val type: String
)

data class IncludedResponse (
    val assets: List<AssetResponse>,
    val participants: List<ParticipantResponse>,
    val rosters: List<RosterResponse>
)

data class MatchAttributesResponse (
    val createdAt: String,
    val duration: Int,
    val matchType: String,
    val gameMode: String,
    val mapName: String,
    val isCustomMatch: Boolean,
    val patchVersion: String?,
    val seasonState: String,
    val shardId: String,
    val stats: Any?,
    val tags: Any?,
    val titleId: String
)

data class MatchRelationshipsResponse (
    val assets: AssetsDataResponse,
    val rosters: RostersDataResponse,
    val rounds: Any?,
    val spectators: Any?
)

data class AssetsDataResponse (
    val data: List<AssetIdResponse>
)

data class AssetIdResponse (
    val type: String,
    val id: String
)

data class AssetResponse (
    val type: String,
    val id: String,
    val attributes: AssetAttributesResponse
)

data class AssetAttributesResponse (
    val URL: String,
    val createdAt: String,
    val description: String?,
    val name: String
)

data class MatchLinksResponse (
    val schema: String?,
    val self: String
)