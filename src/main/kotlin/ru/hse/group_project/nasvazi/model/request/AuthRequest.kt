package ru.hse.group_project.nasvazi.model.request

import ru.hse.group_project.nasvazi.model.enums.UserRole

data class AuthRequest(
    val phone: String,
    val expectedRole: UserRole
) :
    NasvaziRequest
