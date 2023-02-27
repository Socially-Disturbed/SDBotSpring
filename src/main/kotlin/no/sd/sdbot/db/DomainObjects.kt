package no.sd.sdbot.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "sd_guest_score")
data class GuestScore (
    @Id val id: Int,
    @Column("player_name")
    val playerName: String,
    val score: Double,
    val wins: Int,
    val adr: Int,
    val rank: String
)

@Table(name = "sd_score")
data class SdScore (
    @Id val id: Int,
    @Column("player_name")
    val playerName: String,
    val score: Double,
    val wins: Int,
    val adr: Int,
    val rank: String
)