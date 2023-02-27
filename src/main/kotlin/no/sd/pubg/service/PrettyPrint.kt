package no.sd.pubg.service

import no.sd.pubg.api.response.ParticipantStatsResponse

fun ParticipantStatsResponse.prettyPrint(): String {
    return "\n------------------\n" +
            "name='$name'\n" +
            "kills=$kills\n" +
            "assists=$assists\n" +
            "DBNOs=$DBNOs\n" +
            "damageDealt=$damageDealt\n" +
            "revives=$revives\n"
}