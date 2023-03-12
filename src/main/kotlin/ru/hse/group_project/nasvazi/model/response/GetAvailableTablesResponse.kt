package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.dto.AvailableTableDto

data class GetAvailableTablesResponse(
    val tables: List<AvailableTableDto>
) : NasvaziResponse
