package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.group_project.nasvazi.model.dto.UnavailableTableDto
import ru.hse.group_project.nasvazi.model.entity.BookingEntity
import ru.hse.group_project.nasvazi.model.entity.TableEntity
import ru.hse.group_project.nasvazi.repository.BookingRepository
import ru.hse.group_project.nasvazi.repository.TableRepository
import java.time.LocalDate

/**
 * Сервис пользователя
 */
@Service
class TableService(
    private val tableRepository: TableRepository,
    private val bookingRepository: BookingRepository,
) {
    @Transactional
    fun get(name: String): TableEntity {
        return tableRepository.get(name)
    }

    // беру дату на выходе полная инфа по всем столикам за день
    fun getUnavailable(date: LocalDate): List<UnavailableTableDto> {
        // Получаем заброненные времена
        val bookings = bookingRepository.getUnavailableBookingByDate(date.toString())
        val res = bookings.groupBy { it.tableId }.map { (tableId, bookings) ->
            convert(tableId!!, bookings)
        }
        return res
    }

    fun convert(tableId: Long, bookings: List<BookingEntity>): UnavailableTableDto {
        bookings.first().tableId
        val table: TableEntity = tableRepository.get(tableId)
        return UnavailableTableDto(
            name = table.name,
            capacity = table.capacity,
            unavailableStartTime = bookings.map { it.timeFrom!! }
        )
    }
}
