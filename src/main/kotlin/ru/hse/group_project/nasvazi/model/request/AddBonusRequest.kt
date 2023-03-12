package ru.hse.group_project.nasvazi.model.request

data class AddBonusRequest(
    val userId: Long,
    val bonus: Long,
) :
    NasvaziRequest
