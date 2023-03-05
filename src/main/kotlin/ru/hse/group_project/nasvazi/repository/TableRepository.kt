package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.entity.TableEntity
import javax.sql.DataSource

@Repository
class TableRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    dataSource: DataSource
) {
    fun get(name: String): TableEntity {
        val params = mapOf(
            "name" to name,
        )
        return jdbcTemplate.query(SELECT_TABLE_BY_NAME, params, userRowMapper).first()
    }

    private val userRowMapper = RowMapper { rs, _ ->
        TableEntity(
            id = rs.getLong("id"),
            name = rs.getString("name"),
            capacity = rs.getLong("capacity"),
        )
    }
}

private const val SELECT_TABLE_BY_NAME = """
 select * from table where name=:name limit 1
"""
