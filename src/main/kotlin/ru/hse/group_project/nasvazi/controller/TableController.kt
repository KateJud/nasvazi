package ru.hse.group_project.nasvazi.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.request.GetAvailableTablesRequest
import ru.hse.group_project.nasvazi.model.response.GetAvailableTablesResponse
import ru.hse.group_project.nasvazi.model.response.GetUnavailableTablesResponse
import ru.hse.group_project.nasvazi.service.TableService

@RequestMapping("/table")
@RestController
class TableController(
    private val tableService: TableService
) {
    @PostMapping("/unavailable")
    fun getUnavailableTables(request: GetAvailableTablesRequest): GetUnavailableTablesResponse {
        return GetUnavailableTablesResponse(tableService.getUnavailable(request.date))
    }

    @PostMapping("/available")
    fun getAvailableTables(request: GetAvailableTablesRequest): GetAvailableTablesResponse {
        return GetAvailableTablesResponse(tableService.getAvailable(request.date, capacity = request.capacity))
    }

    @PostMapping("/all")
    fun getAllTables() {
    }
}
