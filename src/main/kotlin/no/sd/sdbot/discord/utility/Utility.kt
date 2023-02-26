package no.sd.sdbot.discord.utility

import no.sd.sdbot.db.User

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