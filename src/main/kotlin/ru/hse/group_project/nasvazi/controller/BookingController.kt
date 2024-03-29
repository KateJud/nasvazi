package ru.hse.group_project.nasvazi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.enums.ResponseStatus
import ru.hse.group_project.nasvazi.model.request.CancelBookingRequest
import ru.hse.group_project.nasvazi.model.request.ConfirmBookingRequest
import ru.hse.group_project.nasvazi.model.request.CreateBookingRequest
import ru.hse.group_project.nasvazi.model.response.ActiveBookingResponse
import ru.hse.group_project.nasvazi.model.response.AggregateByDateResponse
import ru.hse.group_project.nasvazi.model.response.CreateBookingResponse
import ru.hse.group_project.nasvazi.model.response.SimpleResponse
import ru.hse.group_project.nasvazi.service.BookingService
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Отвечает за crud модель бронирования столиков
 */
//@Tag(name = "Booking controller")
@RequestMapping("/booking")
@RestController
@CrossOrigin
class BookingController(private val bookingService: BookingService) {

    @Operation(summary = "Создание брони. Передаем источник откуда бронь.")
    @PostMapping(value = ["/create"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun book(
        @RequestBody request: CreateBookingRequest
    ): CreateBookingResponse {
        return bookingService.book(request)
    }

    @Operation(summary = "Для аналитики. [ADMIN CONTROLLER] Агрегация заданий по дате, а внутри по стаусам, по платформе  ")
    @GetMapping(value = ["/aggregate-by-date"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun aggregateByDate(
        @RequestParam("startDate") startDate: LocalDate,
        @RequestParam("endDate") endDate: LocalDate,
    ): AggregateByDateResponse {
        return AggregateByDateResponse(bookingService.aggregate(startDate, endDate))
    }

    @PostMapping(value = ["/cancel"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun cancel(
        @RequestBody request: CancelBookingRequest
    ): SimpleResponse {
        bookingService.cancel(request.bookingId)
        return SimpleResponse(ResponseStatus.SUCCESS)
    }

    @Operation(summary = "Подтверждает бронь")
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

    @Operation(summary = "Выдает список броней по дате")
    @GetMapping(value = ["/by-date"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByDate(@RequestParam("date") date: LocalDate): ActiveBookingResponse {
        val bookings = bookingService.getByDate(date)
        return ActiveBookingResponse(bookings)
    }

    @Operation(summary = "Выдает список броней по дате")
    @GetMapping(value = ["/by-date-time"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByDateTime(@RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) dateTime: LocalDateTime): ActiveBookingResponse {
        val bookings = bookingService.getByDateTime(dateTime)
        return ActiveBookingResponse(bookings)
    }

    @Operation(summary = "Выдает список броней на пользователя")
    @GetMapping(value = ["/by-user"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByUser(@RequestParam("userId") userId: Long): ActiveBookingResponse {
        val bookings = bookingService.getByUser(userId)
        return ActiveBookingResponse(bookings)
    }
}
