package ru.hse.group_project.nasvazi.model.request

import java.time.LocalDate

data class GetAvailableTablesRequest(
    // Дата, на которую искать столики
    val date: LocalDate,
    // Необходимая минимальная вместимость столика
    val capacity: Long,
) :
    NasvaziRequest
