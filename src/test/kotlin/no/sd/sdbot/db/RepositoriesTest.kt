package no.sd.sdbot.db

import no.sd.sdbot.config.DiscordClientConfig
import no.sd.sdbot.discord.Bot
import no.sd.sdbot.testcontainer.TestWithDbContainer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.transaction.annotation.Transactional

@Transactional
class RepositoriesTest: TestWithDbContainer() {

    @Autowired
    lateinit var guestScoreRepository: GuestScoreRepository

    @Autowired
    lateinit var sdScoreRepository: SdScoreRepository

    @MockBean
    lateinit var discordClientConfig: DiscordClientConfig
    @MockBean
    lateinit var bot: Bot

    @Test
    fun testSaveGuestScore() {
        val save = guestScoreRepository.save(
            GuestScore(
                id = 0,
                "testGuest",
                5.5,
                3,
                232,
                "Platinum"
            )
        )

        Assertions.assertThat(guestScoreRepository.findByPlayerName("testGuest")?.playerName)
            .isEqualTo(save.playerName)
    }

    @Test
    fun testSaveSdScore() {
        val save = sdScoreRepository.save(
            SdScore(
                id = 0,
                "testSd",
                5.5,
                3,
                232,
                "Platinum"
            )
        )
        Assertions.assertThat(sdScoreRepository.findByPlayerName("testSd")?.playerName).isNotNull
        Assertions.assertThat(sdScoreRepository.findByPlayerName("testSd")?.playerName).isEqualTo(save.playerName)
    }
}