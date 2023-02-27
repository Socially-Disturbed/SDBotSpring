package no.sd.pubg.api.response

data class ParticipantResponse (
    val type: String,
    val id: String,
    val attributes: ParticipantAttributesResponse,
)

data class ParticipantsDataResponse (
    val data: List<ParticipantIdResponse>
)

data class ParticipantIdResponse (
    val type: String,
    val id: String
)

data class ParticipantAttributesResponse (
    val actor: String?,
    val shardId: String,
    val stats: ParticipantStatsResponse,
)

data class ParticipantStatsResponse (
    val DBNOs: Int,
    val assists: Int,
    val boosts: Int,
    val damageDealt: Double,
    val deathType: String,
    val headshotKills: Int,
    val heals: Int,
    val killPlace: Int,
    val killStreaks: Int,
    val kills: Int,
    val longestKill: Double,
    val name: String,
    val playerId: String,
    val revives: Int,
    val rideDistance: Double,
    val roadKills: Int,
    val swimDistance: Double,
    val teamKills: Int,
    val timeSurvived: Double,
    val vehicleDestroys: Int,
    val walkDistance: Double,
    val weaponsAcquired: Int,
    val winPlace: Int,
) {
    override fun toString(): String {
        return "ParticipantStatsResponse(DBNOs=$DBNOs, assists=$assists, damageDealt=$damageDealt, kills=$kills, name='$name', revives=$revives)"
    }
}