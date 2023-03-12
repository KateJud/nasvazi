package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.group_project.nasvazi.model.dto.AvailableTableDto
import ru.hse.group_project.nasvazi.model.dto.UnavailableTableDto
import ru.hse.group_project.nasvazi.model.entity.BookingEntity
import ru.hse.group_project.nasvazi.model.entity.TableEntity
import ru.hse.group_project.nasvazi.repository.BookingRepository
import ru.hse.group_project.nasvazi.repository.TableRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Сервис пользователя
 */
@Service
class TableService(
    private val tableRepository: TableRepository,
    private val bookingRepository: BookingRepository,
) {
    val BOOKING_DURATION = LocalTime.of(2, 0)
    val POSSIBLE_START_BOOKING_TIME = listOf<LocalTime>(
        LocalTime.of(12, 0),
        LocalTime.of(13, 0),
        LocalTime.of(14, 0),
        LocalTime.of(15, 0),
        LocalTime.of(16, 0),
        LocalTime.of(17, 0),
        LocalTime.of(18, 0),
        LocalTime.of(19, 0),
        LocalTime.of(20, 0),
        LocalTime.of(21, 0),
        LocalTime.of(22, 0),
        LocalTime.of(23, 0),
    )

    @Transactional
    fun get(name: String): TableEntity {
        return tableRepository.get(name)
    }

    fun getAvailable(date: LocalDate, capacity: Long): List<AvailableTableDto> {
        // Получаем столики
        val tables = tableRepository.getByCapacity(capacity)
        // Получаем заброненные времена
        val tableToBookings = bookingRepository.getUnavailableBookingByDate(date.toString()).groupBy { it.tableId }
        // get UnavailableBooking date
        val res = tables.map { table ->

            // Всевозможное время начала бронирования
            val availableStartTimes: MutableList<LocalDateTime> =
                POSSIBLE_START_BOOKING_TIME
                    .map { LocalDateTime.of(date, it) }.toMutableList()

            // Время забронированное другими пользователями
            val unavailableStartTime =
                tableToBookings[table.id]?.flatMap { listOf(it.timeFrom, it.timeFrom.plusHours(1)) }?.mapNotNull { it }
                    ?: listOf()

            // Оставшееся возможное время бронирования
            availableStartTimes.removeAll(unavailableStartTime)

            AvailableTableDto(
                name = table.name,
                capacity = table.capacity,
                availableStartTimes = availableStartTimes
            )
        }

        return res
    }

    // беру дату на выходе полная инфа по всем столикам за день
    fun getUnavailable(date: LocalDate): List<UnavailableTableDto> {
        // Получаем заброненные времена
        val bookings = bookingRepository.getUnavailableBookingByDate(date.toString())
        val res = bookings.groupBy { it.tableId }.map { (tableId, bookings) ->
            convert(tableId, bookings)
        }
        return res
    }

    fun convert(tableId: Long, bookings: List<BookingEntity>): UnavailableTableDto {
        bookings.first().tableId
        val table: TableEntity = tableRepository.get(tableId)
        return UnavailableTableDto(
            name = table.name,
            capacity = table.capacity,
            unavailableStartTime = bookings.map { it.timeFrom }
        )
    }

    fun getAll(): List<TableEntity> {
      return tableRepository.getAll()
    }
}
