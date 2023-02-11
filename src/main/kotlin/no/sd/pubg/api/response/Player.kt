package no.sd.pubg.api.response

data class PlayersResponseWrapper(
    val data: List<PlayerResponse>,
    val meta: Any?
)

data class PlayerResponse (
    val type: String,
    val id: String,
    val attributes: PlayerAttributesResponse,
    val relationships: PlayerRelationshipsResponse,
    val links: PlayerLinksResponse
)

data class PlayerDataResponse (
    val data: PlayerIdResponse
)

data class PlayerIdResponse (
    val type: String,
    val id: String
)

data class PlayerAttributesResponse (
    val name: String,
    val shardId: String,
    val stats: Any?,
    val patchVersion: String,
    val titleId: String
)

data class PlayerRelationshipsResponse (
    val assets: PlayerAssetsResponse,
    val matches: MatchesDataResponse
)

data class PlayerLinksResponse (
    val schema: String,
    val self: String
)

data class PlayerAssetsResponse (
    val data: Any?
)