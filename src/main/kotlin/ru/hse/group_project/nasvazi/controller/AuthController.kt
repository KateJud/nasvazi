package ru.hse.group_project.nasvazi.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.enums.ResponseStatus
import ru.hse.group_project.nasvazi.model.request.LoginAuthRequest
import ru.hse.group_project.nasvazi.model.request.ConfirmPhoneAuthRequest
import ru.hse.group_project.nasvazi.model.response.LoginAuthResponse
import ru.hse.group_project.nasvazi.model.response.SimpleResponse
import ru.hse.group_project.nasvazi.service.UserService

@CrossOrigin
@Tag(name = "Аутентификация ")
@RequestMapping("/auth")
@RestController
class AuthController(
    private val userService: UserService
) {

    @Operation(summary = "Логинизация по номеру")
    /**
     * Get phone
     * Check role
     * for USER create if needed
     * Generate and return check-code
     */
    @PostMapping(value = ["/login"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(
        @RequestBody request: LoginAuthRequest
    ): LoginAuthResponse {
       return userService.login(request)
    }

    // проверка проверочного номера
    // выдача jwt
    @PostMapping(value = ["/confirm-number"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun confirm(
        @RequestBody request: ConfirmPhoneAuthRequest
    ): SimpleResponse {
        // Приходит телефон -- если админ, -- ок; смс -- ок -> jwt
        return SimpleResponse(ResponseStatus.SUCCESS)
    }

    @GetMapping(value = ["/test"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun test(){
        userService.testTwilio()
    }
}
