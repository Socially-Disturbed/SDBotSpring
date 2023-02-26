package no.sd.pubg.domain

data class PlayerId (
    override val id: String
): Id

data class Player(
        val id: PlayerId,
        val name: String,
        val recentMatchIds: Set<MatchId>,
        var rankedStats: RankedPlayerStats?,
)