package ru.hse.group_project.nasvazi.model.response

data class CreateBookingResponse(
    val id: Long,
    val userId: Long,
) : NasvaziResponse
