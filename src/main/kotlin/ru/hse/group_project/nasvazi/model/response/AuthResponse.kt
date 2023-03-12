package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.enums.ResponseStatus

data class AuthResponse(val status: ResponseStatus, val code: Long?) : NasvaziResponse
