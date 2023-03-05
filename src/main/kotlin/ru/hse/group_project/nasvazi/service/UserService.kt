package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.group_project.nasvazi.model.entity.UserEntity
import ru.hse.group_project.nasvazi.repository.UserRepository

/**
 * Сервис пользователя
 */
@Service
class UserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun getOrCreate(phone: String): UserEntity {
        val user = UserEntity(
            phone = phone
        )
        return userRepository.get(phone) ?: userRepository.insert(user)
    }
}
