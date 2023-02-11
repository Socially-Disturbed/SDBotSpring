package no.sd.pubg.domain

data class SeasonId (
    override val id: String
): Id

data class Season (
    val seasonId: SeasonId
)