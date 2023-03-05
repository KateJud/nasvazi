package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import ru.hse.group_project.nasvazi.model.entity.BookingEntity
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
        val user = userService.getOrCreate(request.phone)

        // table  - search by name
        val table = tableService.get(request.name)

        // 1) convert to bookingEntity
        val booking = BookingEntity(
            userId = user.id,
            tableId = table.id,
            timeFrom = request.timeFrom,
            timeTo = request.timeTo,
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

}
