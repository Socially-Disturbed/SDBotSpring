//package no.sd.sdbot.db
//
//import no.sd.sdbot.config.DiscordClientConfig
//import no.sd.sdbot.discord.Bot
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.Test
//import org.postgresql.util.PSQLException
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.transaction.annotation.Transactional
//
//@Transactional
//@SpringBootTest
//class RepositoriesTest {
//
//    @Autowired
//    lateinit var guestScoreRepository: GuestScoreRepository
//
//    @Autowired
//    lateinit var sdScoreRepository: SdScoreRepository
//
//    @Autowired
//    lateinit var warriorRepository: WarriorRepository
//
//    @MockBean
//    lateinit var discordClientConfig: DiscordClientConfig
//    @MockBean
//    lateinit var bot: Bot
//
//    @Test
//    fun testSaveWarrior() {
//        val name = "NewPlayer"
//        warriorRepository.save(
//            Warrior(name = name)
//        )
//        val findByPlayerName = warriorRepository.findByName(name)
//        Assertions.assertThat(findByPlayerName).isNotNull
//    }
//
//    @Test
//    fun testSaveGuestScore() {
//        val warrior = warriorRepository.save(Warrior(name = "newPlayer"))
//
//        guestScoreRepository.save(GuestScore(warriorid = warrior.id))
//
//        val savedGuestScore = guestScoreRepository.findByWarriorName(warrior.name)
//        Assertions.assertThat(savedGuestScore).isNotNull
//
//        val findByWarriorid = guestScoreRepository.findByWarriorid(savedGuestScore!!.warriorid)
//        Assertions.assertThat(findByWarriorid).isNotNull
//    }
//
//    @Test
//    fun testSaveSdScore() {
//
//        val warrior = warriorRepository.save(Warrior(name = "newPlayer"))
//        sdScoreRepository.save(SdScore(warriorid = warrior.id))
//
//        val fetchedSdScore = sdScoreRepository.findByWarriorName(warrior.name)
//        Assertions.assertThat(fetchedSdScore).isNotNull
//
//        val referencedWarrior = warriorRepository.findById(warrior.id).get()
//        Assertions.assertThat(referencedWarrior.name).isEqualTo(warrior.name)
//    }
//
//    @Test
//    fun testSaveSdScoreWithoutWarrior() {
//        Assertions.assertThatThrownBy { sdScoreRepository.save(SdScore(warriorid = 12345)) }
//            .hasRootCauseInstanceOf(PSQLException::class.java)
//    }
//}