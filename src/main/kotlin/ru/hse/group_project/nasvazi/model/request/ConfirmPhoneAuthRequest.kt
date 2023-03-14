package ru.hse.group_project.nasvazi.model.request

data class ConfirmPhoneAuthRequest(
    val userId: Long,
    val code: Long
) :
    NasvaziRequest
