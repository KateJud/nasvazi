package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
//import ru.hse.group_project.nasvazi.config.currentUser
import ru.hse.group_project.nasvazi.model.entity.UserEntity
import ru.hse.group_project.nasvazi.util.toLocalDateTimeUTC
import java.time.Instant
import javax.sql.DataSource

@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    dataSource: DataSource
) {

    private val userJdbcInsert: SimpleJdbcInsert = SimpleJdbcInsert(dataSource).withTableName("item").usingColumns(
        "name",
        "phone",
        "bonus",
        "chat_id",
        "edit_who",
        "edit_date",
        "add_who",
        "add_date",
    ).usingGeneratedKeyColumns("id")

    fun insert(user: UserEntity): UserEntity {
        val params = mapOf(
            "name" to user.name,
            "phone" to user.phone,
            "bonus" to user.bonus,
            "chat_id" to user.chatId,
            "add_date" to Instant.now().toLocalDateTimeUTC(),
            "add_who" to "currentUser",
            "edit_date" to Instant.now().toLocalDateTimeUTC(),
            "edit_who" to "currentUser",
        )
        val id = userJdbcInsert.executeAndReturnKey(params)
        return UserEntity(id.toLong(), user)
    }

    fun get(phone: String): UserEntity? {
        val params = mapOf(
            "phone" to phone,
        )
        return jdbcTemplate.query(SELECT_USER_BY_PHONE, params, userRowMapper).firstOrNull()
    }

    private val userRowMapper = RowMapper { rs, _ ->
        UserEntity(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            phone = rs.getString("phone"),
        )
    }
}

private const val SELECT_USER_BY_PHONE = """
 select * from user where phone=:phone limit 1
"""
