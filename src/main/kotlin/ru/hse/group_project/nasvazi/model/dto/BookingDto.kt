package ru.hse.group_project.nasvazi.model.dto

import ru.hse.group_project.nasvazi.model.enums.BookingStatus
import java.time.LocalDateTime

data class BookingDto(
    val id: Long,
    val userId: Long,
    val userName: String,
    val phone: String,
    val startTime: LocalDateTime,
    val status: BookingStatus,
    val tableId: Long,
    val participants: Long,
    val comment: String?
)
