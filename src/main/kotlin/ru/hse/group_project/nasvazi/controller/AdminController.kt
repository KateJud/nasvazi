package ru.hse.group_project.nasvazi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.response.AggregateBonusByUserResponse
import ru.hse.group_project.nasvazi.service.UserService

/**
 * Отвечает за взаимодействие с администрацией заведения
 */
@RequestMapping("/analytics")
@RestController
@CrossOrigin
class AdminController(
    private val userService: UserService,
) {

    @Operation(summary = "Для аналитики. Агрегация операций с бонусами по пользователю ")
    @GetMapping(value = ["/aggregate-bonus-by-user"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun aggregateByDate(
    ): AggregateBonusByUserResponse {
        return AggregateBonusByUserResponse(userService.aggregate())
    }
}
