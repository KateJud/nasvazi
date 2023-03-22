package ru.hse.group_project.nasvazi.model.entity

import ru.hse.group_project.nasvazi.model.enums.BookingStatus
import ru.hse.group_project.nasvazi.model.enums.PlatformType
import java.time.LocalDateTime

data class BookingEntity
    (
    val id: Long?,
    // @Column(name = "user_id")
    val userId: Long,
    // @Column(name = "table_id")
    val tableId: Long,
    // @Column(name = "time_from")
    val timeFrom: LocalDateTime,
    // @Column(name = "participants")
    val participants: Long = 1L,
    // @Column(name = "status")
    val status: BookingStatus = BookingStatus.CREATED,
    val comment: String?,
    val platform:PlatformType,
)
