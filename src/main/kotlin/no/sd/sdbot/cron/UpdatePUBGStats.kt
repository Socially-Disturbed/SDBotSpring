package no.sd.sdbot.cron

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class UpdatePUBGStats {

    @Scheduled(fixedRate = 1800000)
    fun getPUBGStats() {

    }
}