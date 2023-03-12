package ru.hse.group_project.nasvazi.model.request

data class CancelBookingRequest(
    val bookingId: Long,
) :
    NasvaziRequest
