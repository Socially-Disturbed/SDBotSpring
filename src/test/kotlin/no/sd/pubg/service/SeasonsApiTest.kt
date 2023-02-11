package no.sd.pubg.service

import no.sd.pubg.api.SeasonsApi
import no.sd.pubg.config.ApiClientConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [ApiClientConfig::class, SeasonsApi::class])
class SeasonsApiTest {

    @Autowired
    lateinit var seasonsApi: SeasonsApi

    @Test
    fun testGetSeasons() {
        val seasons = seasonsApi.getAvailableSeasons()
        seasons.size
    }

    @Test
    fun testGetCurrentSeason() {
        val currentSeason = seasonsApi.getCurrentSeason()
        currentSeason.id
    }

    @Test
    fun testGetRankedStats() {
//        val rankedStats = seasonsApi.getRankedStats(
//            PlayerId(type = "player", id = "account.1d9840eb11fc406ebe23a38195a3a4b5"),
//            SeasonId(type = "season", id = "division.bro.official.pc-2018-21")
//        )
//
//        rankedStats
    }
}