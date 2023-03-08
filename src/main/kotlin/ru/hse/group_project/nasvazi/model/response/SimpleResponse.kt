package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.enums.ResponseStatus

data class SimpleResponse(val status: ResponseStatus) : NasvaziResponse
