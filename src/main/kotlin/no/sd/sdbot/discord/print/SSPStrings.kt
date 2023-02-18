package no.sd.sdbot.discord.print

import java.util.*

fun randomStrings(winnerString: String, loserString: String): String {

    val sspStrings = listOf(
        "%s wiped the floor with %s",
        "%s slapped the shit out of %s",
        "%s f..ed up %s hair",
        "%s undressed and manhandled %s",
        "%s decorated his christmas tree with %s",
        "%s fertilized his flowers with %s",
        "%s grisedengte %s",
        "%s felt it was like stealing candy from a child-version of %s",
        "%s used a samekniv on %s",
        "%s bamboozled %s",
        "%s went full Edward Scissorhands on %s",
        "%s was embarrassed by %s play",
        "%s pwned %s",
        "%s played his mental games with %s"
    )
    return String.format(sspStrings[Random().nextInt(sspStrings.size)], winnerString, loserString)
}