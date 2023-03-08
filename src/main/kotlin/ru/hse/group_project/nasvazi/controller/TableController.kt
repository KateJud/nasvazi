package ru.hse.group_project.nasvazi.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.request.GetAvailableTablesRequest
import ru.hse.group_project.nasvazi.model.response.GetAvailableTablesResponse
import ru.hse.group_project.nasvazi.service.TableService

@RequestMapping("/table")
@RestController
class TableController(
    private val tableService: TableService
) {
    @PostMapping("/unavailable")
    fun getFreeTables(request: GetAvailableTablesRequest): GetAvailableTablesResponse {
        return GetAvailableTablesResponse(tableService.getUnavailable(request.date))
    }
}
