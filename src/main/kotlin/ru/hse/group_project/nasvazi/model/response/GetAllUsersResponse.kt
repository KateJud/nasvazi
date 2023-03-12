package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.entity.UserEntity

data class GetAllUsersResponse(
    val users: List<UserEntity>
) :
    NasvaziResponse
