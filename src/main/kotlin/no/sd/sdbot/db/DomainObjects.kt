package no.sd.sdbot.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Embedded.*
import org.springframework.data.relational.core.mapping.Table

data class Warrior (
    @Id val id: Int = 0,
    @Column("player_name") val name: String,
    var adr: Int = 0,
    var rank: String? = null
)

@Table(name = "guest_score")
data class GuestScore (
    @Id val id: Int = 0,
    var score: Double = 0.0,
    var wins: Int = 0,
    val warriorid: Int,
)

@Table(name = "sd_score")
data class SdScore (
    @Id val id: Int = 0,
    var score: Double = 0.0,
    var wins: Int = 0,
    val warriorid: Int,
)

data class ScoreWithWarrior(
    val id: Int,
    val score: Double,
    val wins: Int,
    val warrior: Warrior,
)