package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import ru.hse.group_project.nasvazi.model.entity.BookingEntity
import ru.hse.group_project.nasvazi.model.enums.BookingStatus
import ru.hse.group_project.nasvazi.model.request.CreateBookingRequest
import ru.hse.group_project.nasvazi.repository.BookingRepository

/**
 * Сервис бронирования
 */
@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val userService: UserService,
    private val tableService: TableService,
) {
    fun book(request: CreateBookingRequest): Long {

        // user
        // get Or create user by phoneId
        val user = userService.getOrCreate(request.phone, request.name)

        // table  - search by name
        val table = tableService.get(request.tableName)

        // 1) convert to bookingEntity
        val booking = BookingEntity(
            userId = user.id,
            tableId = table.id,
            timeFrom = request.timeFrom,
            participants = request.participants
        )

        return bookingRepository.insert(booking)
    }

    // todo
    // форма:
    // имя String
    // телефон  String (парсинг на стороне фронта?)
    // столик Long (id ?)
    // время (? диапазон)
    // коммент  String - необязательное поле
    fun cancel(phone: String) {
        bookingRepository.updateStatus(phone, BookingStatus.CANCELLED)
    }

    fun confirm(phone: String) {
        bookingRepository.updateStatus(phone, BookingStatus.CONFIRMED)
    }
}
