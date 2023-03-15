package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.enums.ResponseStatus

data class LoginAuthResponse(
    val status: ResponseStatus,
    val code: Long?,
    val userId: Long?
) : NasvaziResponse
