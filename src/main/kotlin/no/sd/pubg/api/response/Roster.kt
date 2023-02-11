package no.sd.pubg.api.response

data class RosterResponse (
    val type: String,
    val id: String,
    val attributes: RosterAttributesResponse,
    val relationships: RosterRelationshipsResponse,
)

data class RostersDataResponse (
    val data: List<RosterIdResponse>
)

data class RosterIdResponse (
    val type: String,
    val id: String
)

data class RosterAttributesResponse (
    val shardId: String,
    val stats: StatsResponse,
    val won: String,
)

data class StatsResponse (
    val rank: Int,
    val teamId: Int
)

data class RosterRelationshipsResponse (
    val participants: ParticipantsDataResponse,
    val team: Any?
)