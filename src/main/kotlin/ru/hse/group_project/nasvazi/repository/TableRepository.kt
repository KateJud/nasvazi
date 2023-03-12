package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.entity.TableEntity

@Repository
class TableRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {
    fun get(name: String): TableEntity {
        val params = mapOf(
            "name" to name,
        )
        return jdbcTemplate.query(SELECT_TABLE_BY_NAME, params, tableRowMapper).first()
    }

    fun get(id: Long): TableEntity {
        val params = mapOf(
            "id" to id,
        )
        return jdbcTemplate.query(SELECT_TABLE_BY_ID, params, tableRowMapper).first()
    }
    fun getByCapacity(capacity: Long): List<TableEntity> {
        val params = mapOf(
            "capacity" to capacity,
        )
        return jdbcTemplate.query(SELECT_TABLE_BY_CAPACITY, params, tableRowMapper)
    }

    private val tableRowMapper = RowMapper { rs, _ ->
        TableEntity(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            capacity = rs.getLong("capacity"),
        )
    }
}

private const val SELECT_TABLE_BY_NAME = """
 select * from table_ where name=:name limit 1
"""

private const val SELECT_TABLE_BY_ID = """
select * from table_ where id=:id
"""

private const val SELECT_TABLE_BY_CAPACITY = """
 select * from table_ where capacity>=:capacity
"""
