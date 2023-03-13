package ru.hse.group_project.nasvazi.model.dto

import java.time.LocalDateTime

data class UserDto(
    var id: Long,
    var name: String,
    var phone: String,
    var bonus: Long = 0,
    var chatId: Long = 0,
    val addDate: LocalDateTime? = null,
    /**
     * Броней у пльзователя всего
     */
    val totalBookings: Long,
    /**
     * Броней у пользователя отмененных
     */
    val cancelledBookings: Long,
    /**
     * Броней у пользователя подтвержденных
     */
    val confirmedBookings: Long,
)
