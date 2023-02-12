package no.sd.sdbot.discord.print

import no.sd.sdbot.discord.steinsakspapir.SSPResult

fun SSPResult.prettyPrint(): String {
    return if (winner == 0) {
        "${contestants[1]!!.author} and ${contestants[2]!!.author} both picked ${contestants[1]!!.type}. Try again."
    } else {
        val winner = contestants[winner]!!
        "${winner.author} picked ${winner.type} and won this SteinSaksPapir"
    }
}