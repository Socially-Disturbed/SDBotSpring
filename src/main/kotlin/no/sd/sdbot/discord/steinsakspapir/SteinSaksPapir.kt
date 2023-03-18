package no.sd.sdbot.discord.steinsakspapir

import discord4j.core.`object`.entity.channel.MessageChannel
import no.sd.sdbot.discord.command.CommandMessage
import no.sd.sdbot.discord.utility.print.prettyPrint
import no.sd.sdbot.discord.steinsakspapir.SSPType.*
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class SSPHandler {

    var runde: SPPRunde? = null

    fun startOrAddSSPRundeEntry(cmdMsg: CommandMessage): CommandMessage {

        val entry = createSSPEntry(cmdMsg)
        val sspResult = getResult(entry)

        sspResult?.let {
            cmdMsg.returningMsg = it.prettyPrint()
            cmdMsg.returnMsgChannelIds.add("1076223424801816658")
            println("contestant size " + sspResult.contestants.size)
            for (contestant in sspResult.contestants) {
                if (contestant == null) continue
                println(contestant)
                println(contestant!!.dmChannel)
                println(contestant.dmChannel!!.id.asString())
                cmdMsg.returnMsgChannelIds.add(contestant.dmChannel.id.asString())
            }
        } ?: run {
//            cmdMsg.returnMsgChannelIds.add("1076223424801816658")
//            cmdMsg.returningMsg = "STEIN - SAKS - PAPIR \n "
        }
        println("channels size " + cmdMsg.returnMsgChannelIds.size)
        return cmdMsg
    }

    fun createSSPEntry(cmdMsg: CommandMessage): SSPEntry {
        return SSPEntry(
            cmdMsg.message.author.get().username,
            cmdMsg.message.channel.block(),
            SSPType.getSSPType(cmdMsg.getMethodName())!!,
            LocalTime.now()
        )
    }

    fun getResult(entry: SSPEntry): SSPResult? {
        return if (runde == null) {
            runde = SPPRunde().apply { newRundeEntry(entry) }
            null
        } else {
            runde!!.newRundeEntry(entry)
            resolveResult(runde!!.resolve())
        }
    }

    private fun resolveResult(result: SSPResult?): SSPResult? {
        return if (result == null) null
        else {
            runde = null
            result
        }
    }
}

class SPPRunde {
    private val entries: MutableList<SSPEntry> = ArrayList()
    private val dmIds: MutableList<SSPEntry> = ArrayList()

    fun newRundeEntry(entry: SSPEntry) {
        when {
            entries.size == 1 && outdated() -> clearAndAdd(entry)
            else -> entries.add(entry)
        }
    }

    private fun clearAndAdd(entry: SSPEntry) {
        entries.clear()
        entries.add(entry)
    }

    private fun outdated(): Boolean {
        val justNow = LocalTime.now()
        val entryTime = entries[0].time

        val sinceFirst = justNow.minusHours(entryTime.hour.toLong()).minusMinutes(entryTime.minute.toLong())
        return sinceFirst.minute > 1
    }

    fun resolve(): SSPResult? {
        if (entries.size != 2) return null

        val contestants = listOf(null, entries[0], entries[1])
        val result = SSPMatrix(entries[0].type, entries[1].type).resolve()

        return SSPResult(contestants, result)
    }
}

data class SSPResult (
    val contestants: List<SSPEntry?>,
    val winner: Int,
)

data class SSPEntry (
    val author: String,
    val dmChannel: MessageChannel?,
    val type: SSPType,
    val time: LocalTime
)

class SSPMatrix(
    val value1: SSPType,
    val value2: SSPType
) {
    fun resolve(): Int {
        return when {
            value1 == Stein && value2 == Saks -> 1
            value1 == Stein && value2 == Papir -> 2
            value1 == Saks && value2 == Papir -> 1
            value1 == Saks && value2 == Stein -> 2
            value1 == Papir && value2 == Stein -> 1
            value1 == Papir && value2 == Saks -> 2
            else -> 0
        }
    }
}

enum class SSPType {
    Stein,
    Saks,
    Papir;

    companion object {
        fun getSSPType(input: String): SSPType? {
            val types = values().filter { it.name.lowercase() == input.lowercase() }
            return if (types.isNotEmpty()) types.first()
            else null
        }
    }
}