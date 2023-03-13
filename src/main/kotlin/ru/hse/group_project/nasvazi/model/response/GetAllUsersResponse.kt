package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.dto.UserDto

data class GetAllUsersResponse(
    val users: List<UserDto>
) :
    NasvaziResponse
