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

    fun updateGuestRankAdr(user: String, adr: Int, rank: String) {
        val query = "UPDATE \"SD_GUEST_SCORE\" SET \"ADR\" = $adr AND \"RANK\" = \'$rank\' WHERE \"NAME\" = \'$user\'"
        jdbcTemplate.update(query)
    }

    fun updateGuestScore(user: String, score: Float) {
        val query = "UPDATE \"SD_GUEST_SCORE\" SET \"SCORE\" = $score WHERE \"NAME\" = \'$user\'"
        jdbcTemplate.update(query)
    }

    fun updateGuestWin(user: String?) {
        val userResult = getUserGuestScoreBoard(user)
        if (userResult == null) {
            jdbcTemplate.update("INSERT INTO \"SD_GUEST_SCORE\"(\"NAME\", \"WINS\") VALUES ('$user', 1)", userRowMapper)
        }
        else {
            val newWinCount = 1 + userResult.wins
            val query = "UPDATE \"SD_GUEST_SCORE\" SET \"WINS\" = $newWinCount WHERE \"NAME\" = \'$user\'"
            jdbcTemplate.update(query)
        }

    }

    fun getUserGuestScoreBoard(user: String?): User? {
        val result = jdbcTemplate.query("SELECT * FROM \"SD_GUEST_SCORE\" WHERE \"NAME\" = \'$user\'", userRowMapper)
        if (result.isEmpty()) return null
        return jdbcTemplate.query("SELECT * FROM \"SD_GUEST_SCORE\" WHERE \"NAME\" = \'$user\'", userRowMapper)[0]
    }

    fun getGuestScoreBoard(): List<User> {
        return jdbcTemplate.query("SELECT * FROM \"SD_GUEST_SCORE\"", userRowMapper)
    }
}