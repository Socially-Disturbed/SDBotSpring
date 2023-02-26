package no.sd.sdbot.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
class DbService(@Autowired val jdbcTemplate: JdbcTemplate) {
    var userRowMapper: RowMapper<User> = RowMapper<User> { resultSet: ResultSet, rowIndex: Int ->
        User(resultSet.getString("NAME"), resultSet.getFloat("SCORE"),
                resultSet.getInt("WINS"), resultSet.getFloat("ADR"), resultSet.getString("RANK"))
    }

    fun updateSDWin(user: String?) {
        val userResult = getUserSDScoreBoard(user)
        if (userResult == null) {
            jdbcTemplate.update("INSERT INTO \"SD_SCORE\" (\"NAME\", \"WINS\") VALUES ('$user', 1)")
        }
        else {
            val newWinCount = 1 + userResult.wins
            jdbcTemplate.update("UPDATE \"SD_SCORE\" SET \"WINS\" = $newWinCount WHERE \"NAME\" = \'$user\'")
        }
    }

    fun updateSDScore(user: String, score: Float) {
        jdbcTemplate.update("UPDATE \"SD_SCORE\" SET \"SCORE\" = $score WHERE \"NAME\" = \'$user\'")
    }

    fun updateSDRankAdr(user: String, adr: Int, rank: String) {
        jdbcTemplate.update("UPDATE \"SD_SCORE\" SET \"ADR\" = $adr, \"RANK\" = \'$rank\' WHERE \"NAME\" = \'$user\'")
    }

    fun getUserSDScoreBoard(user: String?): User? {
        val result = jdbcTemplate.query("SELECT * FROM \"SD_SCORE\" WHERE \"NAME\" = \'$user\'", userRowMapper)
        if (result.isEmpty()) return null
        return result[0]
    }

    fun updateGuestRankAdr(user: String, adr: Int, rank: String) {
        jdbcTemplate.update("UPDATE \"SD_GUEST_SCORE\" SET \"ADR\" = $adr, \"RANK\" = \'$rank\' WHERE \"NAME\" = \'$user\'")
    }

    fun updateGuestScore(user: String, score: Float) {
        jdbcTemplate.update("UPDATE \"SD_GUEST_SCORE\" SET \"SCORE\" = $score WHERE \"NAME\" = \'$user\'")
    }

    fun updateGuestWin(user: String?) {
        val userResult = getUserGuestScoreBoard(user)
        if (userResult == null) {
            jdbcTemplate.update("INSERT INTO \"SD_GUEST_SCORE\" (\"NAME\", \"WINS\") VALUES ('$user', 1)")
        }
        else {
            val newWinCount = 1 + userResult.wins
            jdbcTemplate.update("UPDATE \"SD_GUEST_SCORE\" SET \"WINS\" = $newWinCount WHERE \"NAME\" = \'$user\'")
        }
    }

    fun getUserGuestScoreBoard(user: String?): User? {
        val result = jdbcTemplate.query("SELECT * FROM \"SD_GUEST_SCORE\" WHERE \"NAME\" = \'$user\'", userRowMapper)
        if (result.isEmpty()) return null
        return result[0]
    }

    fun getAllUsers(): List<User> {
        return jdbcTemplate.query("SELECT * FROM \"SD_GUEST_SCORE\" UNION SELECT * FROM \"SD_SCORE\"", userRowMapper)
    }

    fun getAllSDUsers(): List<User> {
        return jdbcTemplate.query("SELECT * FROM \"SD_SCORE\"", userRowMapper)
    }

    fun getAllGuestUsers(): List<User> {
        return jdbcTemplate.query("SELECT * FROM \"SD_GUEST_SCORE\"", userRowMapper)
    }
}