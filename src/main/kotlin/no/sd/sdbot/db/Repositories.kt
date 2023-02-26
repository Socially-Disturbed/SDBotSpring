package no.sd.sdbot.db

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface CrudRepositoryWithNameFinder<T, Name>: CrudRepository<T, Name>{
    fun findByPlayerName(name: String): T?
}

interface GuestScoreRepository: CrudRepositoryWithNameFinder<GuestScore, String>
interface SdScoreRepository: CrudRepositoryWithNameFinder<SdScore, String>