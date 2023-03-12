package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.dto.BookingDto

data class ActiveBookingResponse(
    val bookings: List<BookingDto>
) : NasvaziResponse
