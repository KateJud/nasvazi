package ru.hse.group_project.nasvazi.model.request

import java.time.LocalDate

data class GetAvailableTablesRequest(
    val date: LocalDate,
) :
    NasvaziRequest
