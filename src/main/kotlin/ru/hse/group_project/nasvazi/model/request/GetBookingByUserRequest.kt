package ru.hse.group_project.nasvazi.model.request

data class GetBookingByUserRequest(
    val userId: Long,
) :
    NasvaziRequest
