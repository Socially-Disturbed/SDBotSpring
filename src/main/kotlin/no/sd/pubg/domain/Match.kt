package no.sd.pubg.domain

data class MatchId(
    override val id: String
): Id

data class Match(
    val matchId: MatchId,
)