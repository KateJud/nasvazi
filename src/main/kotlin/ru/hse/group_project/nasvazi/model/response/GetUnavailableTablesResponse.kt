package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.dto.UnavailableTableDto

data class GetUnavailableTablesResponse(
    val tables: List<UnavailableTableDto>
) : NasvaziResponse
