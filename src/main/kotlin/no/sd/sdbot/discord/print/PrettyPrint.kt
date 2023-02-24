package no.sd.sdbot.discord.print

import no.sd.sdbot.discord.steinsakspapir.SSPEntry
import no.sd.sdbot.db.User
import no.sd.sdbot.discord.steinsakspapir.SSPResult

fun SSPResult.prettyPrint(): String {
    return if (winner == 0) {
        "${contestants[1]!!.author} and ${contestants[2]!!.author} both picked ${contestants[1]!!.type}. Try again."
    } else {
        val winnerEntry: SSPEntry
        val loserEntry: SSPEntry

        if (winner == 1) {
            winnerEntry = contestants[1]!!
            loserEntry = contestants[2]!!
        } else {
            winnerEntry = contestants[2]!!
            loserEntry = contestants[1]!!
        }
        "${randomStrings(winnerEntry.author, loserEntry.author)}, and won this SteinSaksPapir {${winnerEntry.type} vs ${loserEntry.type}}"
    }
}

fun User.prettyPrint(): String {
    return "\n-------------------------\n" +
           "${name} - ${rank}\n" +
           "Wins: ${wins}\n" +
           "ADR: ${adr}\n" +
           "-------------------------"
}

fun userListToPrint(users: List<User>): String {
    var print = ""
    for (user in users)
        print += user.prettyPrint()

    return print
}