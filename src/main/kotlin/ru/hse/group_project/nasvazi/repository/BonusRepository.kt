package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.entity.BonusTrnEntity
import javax.sql.DataSource

@Repository
class BonusRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val dataSource: DataSource

) {

    private val bonusTrnJdbcInsert: SimpleJdbcInsert =
        SimpleJdbcInsert(dataSource).withTableName("booking").usingColumns(
            "user_id",
            "qty",
            "type",
            "date_when",
        ).usingGeneratedKeyColumns("id")

    fun insert(bonusTrn: BonusTrnEntity): Long {
        val params = mapOf(
            "user_id" to bonusTrn.userId,
            "qty" to bonusTrn.qty,
            "type" to bonusTrn.type.name,
            "date_when" to bonusTrn.dateWhen,
        )
        val id = bonusTrnJdbcInsert.executeAndReturnKey(params)
        return id.toLong()
    }
}
