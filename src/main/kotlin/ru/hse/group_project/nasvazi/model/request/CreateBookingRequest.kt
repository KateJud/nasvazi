package ru.hse.group_project.nasvazi.model.request

import java.time.LocalDateTime

data class CreateBookingRequest(
    val userId: Long?,
    val name: String?,
    val phone: String?,
    val participants: Long,
    val tableName: String,
    val timeFrom: LocalDateTime,
    val comment: String?
) :
    NasvaziRequest
