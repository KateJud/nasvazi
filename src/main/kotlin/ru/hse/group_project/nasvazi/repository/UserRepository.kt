package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.entity.CodeEntity
import ru.hse.group_project.nasvazi.model.entity.RoleEntity
import ru.hse.group_project.nasvazi.model.entity.UserEntity
import ru.hse.group_project.nasvazi.model.enums.UserRole
import javax.sql.DataSource

@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    dataSource: DataSource
) {

    private val userJdbcInsert: SimpleJdbcInsert = SimpleJdbcInsert(dataSource).withTableName("user_").usingColumns(
        "name",
        "phone",
        "bonus",
        "chat_id",
    ).usingGeneratedKeyColumns("id")

    fun create(user: UserEntity): UserEntity {
        val params = mapOf(
            "name" to user.name,
            "phone" to user.phone,
            "bonus" to user.bonus,
            "chat_id" to user.chatId,
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

    fun getById(id: Long): UserEntity {
        val params = mapOf(
            "id" to id,
        )
        return jdbcTemplate.query(SELECT_USER_BY_ID, params, userRowMapper).first()
    }

    fun getAll(): List<UserEntity> {
        return jdbcTemplate.query(SELECT_ALL_USERS, userRowMapper)
    }

    fun addBonus(userId: Long, bonus: Long) {
        val params = mapOf(
            "bonus" to bonus,
            "id" to userId
        )
        jdbcTemplate.update(ADD_BONUS, params)
    }

    fun createRole(userId: Long, role: UserRole) {
        val params = mapOf(
            "userId" to userId,
            "roleId" to role.id,
        )
        jdbcTemplate.update(INSERT_USER_ROLE_REF, params)
    }

    fun getRoles(userId: Long): List<UserRole> {
        val params = mapOf(
            "id" to userId,
        )
        return jdbcTemplate.query(GET_USER_ROLES, params, roleMapper).map { it.name }
    }

    fun createCode(code: CodeEntity) {
        val params = mapOf(
            "userId" to code.userId,
            "code" to code.code,
        )
        jdbcTemplate.update(INSERT_USER_CODE, params)
    }

    private val userRowMapper = RowMapper { rs, _ ->
        UserEntity(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            phone = rs.getString("phone"),
            bonus = rs.getLong("bonus"),
            addDate =rs.getTimestamp("add_date").toLocalDateTime()
        )
    }

    private val roleMapper = RowMapper { rs, _ ->
        RoleEntity(
            name = UserRole.valueOf(rs.getString("name")),
        )
    }
}

private const val SELECT_USER_BY_PHONE = """
 select * from user_ where phone=:phone limit 1
"""

private const val INSERT_USER_ROLE_REF = """
insert into user_role_ref (user_id, role_id)
values (:userId, :roleId)
"""

private const val GET_USER_ROLES = """
select r.name
from role r
         join user_role_ref urr on r.id = urr.role_id
         join user_ u on urr.user_id = u.id
where u.id = :id
"""

private const val INSERT_USER_CODE = """
insert into user_code (user_id, code)
VALUES (:userId, :code);
"""

private const val SELECT_USER_BY_ID = """
 select * from user_ where id=:id
"""

private const val SELECT_ALL_USERS = """
 select * from user_
"""

private const val ADD_BONUS = """
update user_ u
set bonus = bonus + :bonus
where u.id=:id
"""
