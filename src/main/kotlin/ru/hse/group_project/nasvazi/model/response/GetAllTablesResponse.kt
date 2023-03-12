package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.entity.TableEntity

data class GetAllTablesResponse(
    val tables: List<TableEntity>
) :
    NasvaziResponse
