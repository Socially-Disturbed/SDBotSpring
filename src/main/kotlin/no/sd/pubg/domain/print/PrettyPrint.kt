package no.sd.pubg.domain.print

import no.sd.pubg.domain.Player

fun Player.prettyPrint(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("name: $name").append("\n")
    stringBuilder.append("playerid: {${id.id}}").append("\n")
    stringBuilder.append("last 5 matches: ").append("\n")
    val last5 = recentMatchIds.take(5)
    last5.forEachIndexed { index, matchId -> stringBuilder.append("${index+1}: {${matchId.id}}").append("\n") }

    return stringBuilder.toString()
}