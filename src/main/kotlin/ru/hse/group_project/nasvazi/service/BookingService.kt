package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import ru.hse.group_project.nasvazi.repository.BookingRepository
import ru.hse.group_project.nasvazi.repository.TableRepository
import ru.hse.group_project.nasvazi.repository.UserRepository

/**
 * Сервис бронирования
 */
@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val tableRepository: TableRepository,
    private val userRepository: UserRepository,
) {
}
