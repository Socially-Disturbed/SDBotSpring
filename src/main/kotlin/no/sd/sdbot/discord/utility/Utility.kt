package no.sd.sdbot.discord.utility

import discord4j.core.spec.EmbedCreateSpec
import no.sd.sdbot.db.User
import no.sd.sdbot.discord.utility.print.prettyNewPrintWithoutName

fun userListToStringSetWithNames(users: List<User> ): Set<String> {
    val usernames = HashSet<String>()
    for (user in users) {
        usernames.add(user.name)
    }
    return usernames
}

fun stringSetToStringWithDelim(stringSet: Set<String>, delim: String): String {
    val sb = StringBuilder()
    var loopDelim = ""
    for (s in stringSet) {
        sb.append(loopDelim)
        sb.append(s)

        loopDelim = delim
    }
    return sb.toString()
}

fun usersToEmbedField(embedBuilder: EmbedCreateSpec.Builder, users: List<User>): EmbedCreateSpec.Builder {
    var firstInList = true
    var fieldCount = 0
    for (user in users) {
        if (firstInList) {
            embedBuilder.addField("", "", true)
            embedBuilder.addField(user.name, user.prettyNewPrintWithoutName(), true)
            embedBuilder.addField("", "", true)
            firstInList = false
        }
        else {
            embedBuilder.addField(user.name, user.prettyNewPrintWithoutName(), true)
            fieldCount++
            if (fieldCount == 3) fieldCount = 0
        }
    }
    while (fieldCount < 3) {
        embedBuilder.addField("", "", true)
        fieldCount++
    }
    return embedBuilder
}