package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import ru.hse.group_project.nasvazi.model.dto.BookingDto
import ru.hse.group_project.nasvazi.model.entity.BookingEntity
import ru.hse.group_project.nasvazi.model.entity.TableEntity
import ru.hse.group_project.nasvazi.model.entity.UserEntity
import ru.hse.group_project.nasvazi.model.enums.BookingStatus
import ru.hse.group_project.nasvazi.model.request.CreateBookingRequest
import ru.hse.group_project.nasvazi.model.response.CreateBookingResponse
import ru.hse.group_project.nasvazi.repository.BookingRepository
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Сервис бронирования
 */
@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val userService: UserService,
    private val tableService: TableService,
) {

    private val BOOKING_TIME = 2L

    fun book(request: CreateBookingRequest): CreateBookingResponse {

        // user
        // get Or create user by phoneId
        val user = userService.getOrCreate(request.phone, request.name)

        // table  - search by name
        val table = tableService.get(request.tableName)
        checkTableIsAvailableAtTime(table, request.timeFrom)

        // 1) convert to bookingEntity
        val booking = BookingEntity(
            id = null,
            userId = user.id!!,
            tableId = table.id!!,
            timeFrom = request.timeFrom,
            participants = request.participants,
            comment = request.comment,
        )

        val bookingId = bookingRepository.insert(booking)
        return CreateBookingResponse(id = bookingId, userId = user.id!!)
    }

    private fun checkTableIsAvailableAtTime(table: TableEntity, startBookingTime: LocalDateTime) {
        if (bookingRepository.getByTimeRange(table.id!!,
                startBookingTime.minusHours(1),
                startBookingTime.plusHours(1))
                .isNotEmpty()
        )
            throw IllegalArgumentException("Table with tableId ${table.id} is not free at $startBookingTime")
    }

    fun cancel(id: Long) {
        bookingRepository.updateStatusById(id, BookingStatus.CANCELLED)
    }

    fun confirm(id: Long) {
        bookingRepository.updateStatusById(id, BookingStatus.CONFIRMED)
    }

    fun getActive(): List<BookingDto> {
        return bookingRepository.getAll().map {
            convertToBookingDto(it)
        }
    }

    fun getByDate(date: LocalDate): List<BookingDto> {
        return bookingRepository.getByDate(date).map {
            convertToBookingDto(it)
        }
    }

    fun getByDateTime(date: LocalDateTime): List<BookingDto> {
        return bookingRepository.getByDateTime(date).map {
            convertToBookingDto(it)
        }
    }

    fun getByUser(userId: Long): List<BookingDto> {
        return bookingRepository.getByUser(userId).map {
            convertToBookingDto(it)
        }
    }

    private fun convertToBookingDto(it: BookingEntity): BookingDto {
        val user: UserEntity = userService.getById(it.userId)
        return BookingDto(
            id = it.id!!,
            tableId = it.tableId,
            participants = it.participants,
            comment = it.comment,
            userId = it.userId,
            userName = user.name,
            phone = user.phone,
            startTime = it.timeFrom,
            status = it.status
        )
    }
}
