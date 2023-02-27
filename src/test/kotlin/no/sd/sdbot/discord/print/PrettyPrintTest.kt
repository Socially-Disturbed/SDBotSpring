package no.sd.sdbot.discord.print

import no.sd.sdbot.discord.steinsakspapir.SSPEntry
import no.sd.sdbot.discord.steinsakspapir.SSPResult
import no.sd.sdbot.discord.steinsakspapir.SSPType
import no.sd.sdbot.discord.utility.print.prettyPrint
import org.junit.jupiter.api.Test
import java.time.LocalTime

class PrettyPrintTest {





    @Test
    fun testPrettyPrint() {

        val sspResult = SSPResult(
            listOf(
                null,
                SSPEntry("Dytt", SSPType.Saks, LocalTime.now()),
                SSPEntry("Mezzy", SSPType.Saks, LocalTime.now())
            ),
            1
        )

        val prettyPrint = sspResult.prettyPrint()
        val prettyPrint1 = sspResult.prettyPrint()
        val prettyPrint2 = sspResult.prettyPrint()
        val prettyPrint3 = sspResult.prettyPrint()
    }
}