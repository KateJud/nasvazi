package ru.hse.group_project.nasvazi.model.entity

import ru.hse.group_project.nasvazi.model.enums.BookingStatus
import java.time.Instant
import java.time.LocalDateTime

data class BookingEntity
    (
    // @Column(name = "user_id")
    val userId: Long? = null,
    // @Column(name = "table_id")
    val tableId: Long? = null,
    // @Column(name = "time_from")
    val timeFrom: LocalDateTime? = null,
    // @Column(name = "time_to")
    val timeTo: Instant? = null,
    // @Column(name = "participants")
    val participants: Long = 1L,
    // @Column(name = "status")
    val status: BookingStatus = BookingStatus.CREATED
)
