package ru.hse.group_project.nasvazi.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.hse.group_project.nasvazi.model.entity.TableEntity
import ru.hse.group_project.nasvazi.repository.TableRepository

/**
 * Сервис пользователя
 */
@Service
class TableService(
    private val tableRepository: TableRepository,
) {
    @Transactional
    fun get(name: String): TableEntity {
        return tableRepository.get(name)
    }
}
