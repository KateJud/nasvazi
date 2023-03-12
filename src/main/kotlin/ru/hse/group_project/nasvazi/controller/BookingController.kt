package ru.hse.group_project.nasvazi.controller

// import io.swagger.v3.oas.annotations.Operation
// import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.enums.ResponseStatus
import ru.hse.group_project.nasvazi.model.request.CancelBookingRequest
import ru.hse.group_project.nasvazi.model.request.ConfirmBookingRequest
import ru.hse.group_project.nasvazi.model.request.CreateBookingRequest
import ru.hse.group_project.nasvazi.model.response.ActiveBookingResponse
import ru.hse.group_project.nasvazi.model.response.CreateBookingResponse
import ru.hse.group_project.nasvazi.model.response.SimpleResponse
import ru.hse.group_project.nasvazi.service.BookingService

/**
 * Отвечает за crud модель бронирования столиков
 */
//@Tag(name = "Booking controller")
@RequestMapping("/booking")
@RestController
class BookingController(private val bookingService: BookingService) {

    //  @Operation(summary = "Create ")
    @PostMapping(value = ["/create"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun book(
        @RequestBody request: CreateBookingRequest
    ): CreateBookingResponse {
        val bookingId = bookingService.book(request)
        return CreateBookingResponse(id = bookingId)
    }

    @PostMapping(value = ["/cancel"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun cancel(
        @RequestBody request: CancelBookingRequest
    ): SimpleResponse {
        bookingService.cancel(request.bookingId)
        return SimpleResponse(ResponseStatus.SUCCESS)
    }

    @PostMapping(value = ["/confirm"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun confirm(
        @RequestBody request: ConfirmBookingRequest
    ): SimpleResponse {
        bookingService.confirm(request.bookingId)
        return SimpleResponse(ResponseStatus.SUCCESS)
    }

    @Operation(summary = "Выдает список активных броней")
    @GetMapping(value = ["/active"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getActive(): ActiveBookingResponse {
        val bookings = bookingService.getActive()
        return ActiveBookingResponse(bookings)
    }
}
