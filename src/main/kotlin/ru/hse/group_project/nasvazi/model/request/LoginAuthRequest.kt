package ru.hse.group_project.nasvazi.model.request

import ru.hse.group_project.nasvazi.model.enums.UserRole

data class LoginAuthRequest(
    val phone: String,
    val expectedRole: UserRole,
    val name: String,
    val chatId: Long,
) :
    NasvaziRequest
