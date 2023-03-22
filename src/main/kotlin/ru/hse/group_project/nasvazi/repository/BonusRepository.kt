package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.dto.AnalysisBonusDto
import ru.hse.group_project.nasvazi.model.entity.BonusTrnEntity
import javax.sql.DataSource

@Repository
class BonusRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val dataSource: DataSource

) {

    private val bonusTrnJdbcInsert: SimpleJdbcInsert =
        SimpleJdbcInsert(dataSource).withTableName("bonus_trn").usingColumns(
            "user_id",
            "qty",
            "type",
            "time_when",
        ).usingGeneratedKeyColumns("id")

    fun insert(bonusTrn: BonusTrnEntity): Long {
        val params = mapOf(
            "user_id" to bonusTrn.userId,
            "qty" to bonusTrn.qty,
            "type" to bonusTrn.type.name,
            "time_when" to bonusTrn.timeWhen,
        )
        val id = bonusTrnJdbcInsert.executeAndReturnKey(params)
        return id.toLong()
    }

    fun aggregate(): List<AnalysisBonusDto> {

        return jdbcTemplate.query(
            AGGREGATE_BOOKING_BY_USER,
            EmptySqlParameterSource.INSTANCE,
            analysisBonusDtoRowMapper
        )
    }

    private val analysisBonusDtoRowMapper = RowMapper { rs, _ ->
        AnalysisBonusDto(
            qtyAdd = rs.getLong("qty_add"),
            qtyReduce = rs.getLong("qty_reduce"),
            userId = rs.getLong("user_id"),
        )
    }
}

const val AGGREGATE_BOOKING_BY_USER = """
select bt.user_id,
       SUM(qty) FILTER (where bt.type = 'ADD')    as qty_add,
       SUM(qty) FILTER (where bt.type = 'REDUCE') as qty_reduce

from bonus_trn bt
group by bt.user_id
;"""
