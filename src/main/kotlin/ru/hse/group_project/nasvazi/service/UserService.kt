package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import ru.hse.group_project.nasvazi.repository.BookingRepository
import ru.hse.group_project.nasvazi.repository.UserRepository

/**
 * Сервис пользователя
 */
@Service
class UserService(
    private val userRepository: UserRepository,
) {
}
