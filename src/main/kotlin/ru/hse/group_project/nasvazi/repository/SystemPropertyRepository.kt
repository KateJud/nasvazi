package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.entity.SystemPropertyEntity
import ru.hse.group_project.nasvazi.model.enums.NsqlConfigKey

@Repository
class SystemPropertyRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) {

    fun getSystemProperty(name: NsqlConfigKey): SystemPropertyEntity? {
        val params = mapOf(
            "name" to name.name,
        )
        return jdbcTemplate.query(GET_CONFIG_VALUE, params, systemPropertyRowMapper).firstOrNull()
    }

    private val systemPropertyRowMapper = RowMapper { rs, _ ->
        SystemPropertyEntity(
            name = rs.getString("name"),
            value = rs.getString("value")
        )
    }
}

private const val GET_CONFIG_VALUE = """
select *
from system_property
where name = :name
"""
