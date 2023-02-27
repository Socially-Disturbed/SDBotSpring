package no.sd.sdbot.discord.utility.print

import java.util.*

fun randomStrings(winnerString: String, loserString: String): String {

    val sspStrings = listOf(
        "%s wiped the floor with %s",
        "%s slapped the shit out of %s",
        "%s f..ed up %s hair",
        "%s undressed and manhandled %s",
        "%s decorated his christmas tree with %s",
        "%s fertilized his flowers with %s",
        "%s felt it was like stealing candy from a child-version of %s",
        "%s slayed %s with Vebis' samekniv",
        "%s bamboozled %s",
        "%s went full Edward Scissorhands on %s",
        "%s was embarrassed by %s weak play",
        "%s pwned %s",
        "%s played his mental games with %s",
        "%s went full Mezzy mode and whined %s to death",
        "%s used Thøøtles tortoiseshell to crush %s",
        "%s is a 1337 kinda SSP-player, ownd %s",
        "%s bitch slapped %s",
        "%s showed %s his amazing skills",
        "%s cåkkblåkkt %s"
    )
    return String.format(sspStrings[Random().nextInt(sspStrings.size)], winnerString, loserString)
}