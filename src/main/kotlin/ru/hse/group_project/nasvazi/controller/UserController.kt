package ru.hse.group_project.nasvazi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.enums.ResponseStatus
import ru.hse.group_project.nasvazi.model.request.AddBonusRequest
import ru.hse.group_project.nasvazi.model.response.GetAllUsersResponse
import ru.hse.group_project.nasvazi.model.response.SimpleResponse
import ru.hse.group_project.nasvazi.service.UserService

/**
 * Отвечает за crud информацию о пользователях системы (работниках/клиентах)
 */
@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @Operation(summary = "Возвращает информацию о всех пользователях")
    @GetMapping("/all")
    fun getAll(): GetAllUsersResponse {
        val users = userService.getAll()
        return GetAllUsersResponse(users)
    }

    @Operation(summary = "Накапливает n балов пользователю")
    @PostMapping("/add-bonus")
    fun addBonus(@RequestBody request: AddBonusRequest): SimpleResponse {
        userService.addBonus(id = request.userId, bonus = request.bonus)
        return SimpleResponse(ResponseStatus.SUCCESS)
    }

    @Operation(summary = "Снимает n баллов с пользователя")
    @PostMapping("/reduce-bonus")
    fun reduceBonus(@RequestBody request: AddBonusRequest): SimpleResponse {
        userService.addBonus(id = request.userId, bonus = -request.bonus)
        return SimpleResponse(ResponseStatus.SUCCESS)
    }
}
