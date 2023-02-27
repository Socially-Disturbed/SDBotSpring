package no.sd.sdbot.db

import org.springframework.stereotype.Service

@Service
class DbService(
    val warriorRepository: WarriorRepository,
    val sdRepository: SdScoreRepository,
    val guestRepository: GuestScoreRepository,
) {

    private fun getOrCreateWarrior(name: String): Warrior {
        return warriorRepository.findByName(name)
            ?: warriorRepository.save(Warrior(name = name))
    }

    fun updateSDWin(name: String) {
        val warrior = getOrCreateWarrior(name)

        sdRepository.findByWarriorName(warrior.name)?.let {
            it.wins = it.wins + 1
            sdRepository.save(it)
        } ?: sdRepository.save(SdScore(warriorid = warrior.id, wins = 1))
    }

    fun updateSDScore(name: String, score: Double) {
        val warrior = getOrCreateWarrior(name)
        sdRepository.findByWarriorName(warrior.name)?.let {
            it.score = score
            sdRepository.save(it)
        }
    }

    fun updateSDRankAdr(name: String, adr: Int, rank: String) {
        warriorRepository.save(
            getOrCreateWarrior(name).apply {
                this.adr = adr
                this.rank = rank
            }
        )
    }

    fun updateGuestRankAdr(name: String, adr: Int, rank: String) {
        warriorRepository.save(
            getOrCreateWarrior(name).apply {
                this.adr = adr
                this.rank = rank
            }
        )
    }

    fun updateGuestScore(name: String, score: Double) {
        val warrior = getOrCreateWarrior(name)
        guestRepository.findByWarriorName(warrior.name)?.let {
            it.score = score
            guestRepository.save(it)
        }
    }

    fun updateGuestWin(name: String) {
        val warrior = getOrCreateWarrior(name)

        guestRepository.save(
            guestRepository.findByWarriorName(warrior.name)?.apply {
                this.wins = this.wins + 1
            } ?: GuestScore(warriorid = warrior.id, wins = 1)
        )
    }

    fun getAllWarriors(): List<Warrior> {
        return warriorRepository.findAll().toList()
    }

    fun getAllSDUsers(): List<ScoreWithWarrior> {
        return sdRepository.findAll().toList()
            .map { with(it) { toScoreWithWarrior(id, score, wins, warriorid) } }
    }

    fun getAllGuestUsers(): List<ScoreWithWarrior> {
        return guestRepository.findAll().toList()
            .map { with(it) { toScoreWithWarrior(id, score, wins, warriorid) } }
    }
}

fun DbService.toScoreWithWarrior(
    id: Int, score: Double, wins: Int, warriorid: Int
): ScoreWithWarrior {
    val warrior = warriorRepository.findById(warriorid).get()

    return ScoreWithWarrior(
        id = id,
        score = score,
        wins = wins,
        warrior = warrior
    )
}