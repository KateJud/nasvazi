package ru.hse.group_project.nasvazi.model.request

import ru.hse.group_project.nasvazi.model.enums.PlatformType
import java.time.LocalDateTime

data class CreateBookingRequest(
    val userId: Long?,
    val name: String?,
    val phone: String?,
    val participants: Long,
    val tableName: String,
    val timeFrom: LocalDateTime,
    val comment: String?,
    val platform: PlatformType = PlatformType.UNKNOWN,

    ) :
    NasvaziRequest
