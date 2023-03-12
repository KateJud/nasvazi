package ru.hse.group_project.nasvazi.model.request

data class ConfirmBookingRequest(
    val bookingId: Long,
) :
    NasvaziRequest
