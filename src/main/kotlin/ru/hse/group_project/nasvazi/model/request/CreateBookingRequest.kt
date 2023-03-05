package ru.hse.group_project.nasvazi.model.request

import java.time.Instant

data class CreateBookingRequest(
    val name: String,
    val phone: String,
    val participants: Long,
    val tableId: Long,
    val timeFrom: Instant,
    val timeTo: Instant,
    val comment: String
) :
    NasvaziRequest
