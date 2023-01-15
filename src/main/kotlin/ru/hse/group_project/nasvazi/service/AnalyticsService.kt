package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import ru.hse.group_project.nasvazi.repository.BookingRepository
import ru.hse.group_project.nasvazi.repository.UserRepository

/**
 * Сервис для сбора аналитики
 */
@Service
class AnalyticsService(
    private val bookingRepository: BookingRepository,
    private val userRepository: UserRepository,
) {
}
