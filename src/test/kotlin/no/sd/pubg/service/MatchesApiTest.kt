package no.sd.pubg.service
//
//import no.sd.pubg.config.ApiClientConfig
//import no.sd.pubg.api.MatchesApi
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.test.context.ContextConfiguration
//
//@SpringBootTest
//@ContextConfiguration(classes = [ApiClientConfig::class, MatchesApi::class])
//class MatchesApiTest {
//
//    @Autowired
//    lateinit var matchesApi: MatchesApi
//
//    @Test
//    fun testMatchesApi() {
//        val matchResponse = matchesApi.getMatch("28ca0f20-7d59-4d0b-8433-14aee5b2cba9")
//        matchResponse.data
//    }
//}