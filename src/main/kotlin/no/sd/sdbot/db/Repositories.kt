package no.sd.sdbot.db

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

interface WarriorRepository: CrudRepository<Warrior, Int> {
    fun findByName(name: String): Warrior?
}

@NoRepositoryBean
interface CommonWarriorReferenceRepository<T, Warriorid>: CrudRepository<T, Warriorid> {
    fun findByWarriorid(id:Int): T?
}

interface GuestScoreRepository: CommonWarriorReferenceRepository<GuestScore, Int> {
    @Query("select gs.* from guest_score gs join warrior w on w.id = gs.warriorid where w.player_name = :name")
    fun findByWarriorName(name: String): GuestScore?
}

interface SdScoreRepository: CommonWarriorReferenceRepository<SdScore, Int> {
    @Query("select sds.* from sd_score sds join warrior w on w.id = sds.warriorid where w.player_name = :name")
    fun findByWarriorName(name: String): SdScore?
}