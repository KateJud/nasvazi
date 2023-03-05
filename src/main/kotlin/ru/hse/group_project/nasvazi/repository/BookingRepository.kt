package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.entity.BookingEntity
import ru.hse.group_project.nasvazi.util.toLocalDateTimeUTC
import java.time.Instant
import javax.sql.DataSource

@Repository
class BookingRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    dataSource: DataSource
) {
    val currentUser = "TEST"

    private val bookingJdbcInsert: SimpleJdbcInsert = SimpleJdbcInsert(dataSource).withTableName("item").usingColumns(
        "user_id",
        "table_id",
        "time_from",
        "time_to",
        "participants",
        "status",
        "edit_who",
        "edit_date",
        "add_who",
        "add_date",
    ).usingGeneratedKeyColumns("id")

    fun insert(booking: BookingEntity): Long {
        val params = mapOf(
            "user_id" to booking.userId,
            "table_id" to booking.tableId,
            "time_from" to booking.timeFrom,
            "time_to" to booking.timeTo,
            "participants" to booking.participants,
            "status" to booking.status.name,
            "add_date" to Instant.now().toLocalDateTimeUTC(),
            "add_who" to currentUser,
            "edit_date" to Instant.now().toLocalDateTimeUTC(),
            "edit_who" to currentUser,
        )
        val id = bookingJdbcInsert.executeAndReturnKey(params)
        return id.toLong()
    }
}
