package ru.hse.group_project.nasvazi.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.entity.BookingEntity
import ru.hse.group_project.nasvazi.model.enums.BookingStatus
import java.time.LocalDate
import java.time.LocalDateTime
import javax.sql.DataSource

@Repository
class BookingRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val dataSource: DataSource
) {

    private val bookingJdbcInsert: SimpleJdbcInsert =
        SimpleJdbcInsert(dataSource).withTableName("booking").usingColumns(
            "user_id",
            "table_id",
            "time_from",
            "participants",
            "status",
            "comment"
        ).usingGeneratedKeyColumns("id")

    fun insert(booking: BookingEntity): Long {
        val params = mapOf(
            "user_id" to booking.userId,
            "table_id" to booking.tableId,
            "time_from" to booking.timeFrom,
            "participants" to booking.participants,
            "status" to booking.status.name,
            "comment" to booking.comment
        )
        val id = bookingJdbcInsert.executeAndReturnKey(params)
        return id.toLong()
    }

    fun updateStatusById(id: Long, status: BookingStatus) {
        val params = mapOf(
            "status" to status.name,
            "id" to id
        )
        jdbcTemplate.update(UPDATE_STATUS_BY_ID, params)
    }

    fun getUnavailableBookingByDate(date: String): List<BookingEntity> {
        val params = mapOf(
            "date" to date,
            "status" to BookingStatus.CANCELLED.name,
        )
        return jdbcTemplate.query(SELECT_BOOKING_BY_DATE_AND_STATUS, params, bookingRowMapper)
    }

    fun getAll(): List<BookingEntity> {
        return jdbcTemplate.query(SELECT_ALL, bookingRowMapper)
    }

    fun getByDate(date: LocalDate): List<BookingEntity> {
        val params = mapOf(
            "date" to date,
        )
        return jdbcTemplate.query(SELECT_BY_DATE, params, bookingRowMapper)
    }

    fun getByDateTime(dateTime: LocalDateTime): List<BookingEntity> {
        val params = mapOf(
            "dateTime" to dateTime,
        )
        return jdbcTemplate.query(SELECT_BY_DATE_TIME, params, bookingRowMapper)
    }

    fun getByUser(userId: Long): List<BookingEntity> {
        val params = mapOf(
            "userId" to userId,
        )
        return jdbcTemplate.query(SELECT_BY_USER, params, bookingRowMapper)
    }

    private val bookingRowMapper = RowMapper { rs, _ ->
        BookingEntity(
            id = rs.getLong("id"),
            userId = rs.getLong("user_id"),
            tableId = rs.getLong("table_id"),
            timeFrom = rs.getTimestamp("time_from").toLocalDateTime(),
            participants = rs.getLong("participants"),
            status = BookingStatus.valueOf(rs.getString("status")),
            comment = rs.getString("comment")
        )
    }
}

const val UPDATE_STATUS_BY_ID = """
update booking b
set status = :status
where b.id = :id
"""

const val SELECT_BOOKING_BY_DATE_AND_STATUS = """
select *
from booking
where to_date(time_from,'dd-MM-yyyy') = :date
and status != :status
"""

const val SELECT_ALL = """
select *
from booking
"""

const val SELECT_BY_DATE = """
select *
from booking
where to_date(time_from::text,'yyyy-MM-dd') = :date
"""

const val SELECT_BY_DATE_TIME = """
select *
from booking
where time_from =:dateTime
"""

const val SELECT_BY_USER = """
select *
from booking
where user_id = :userId
"""
