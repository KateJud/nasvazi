package ru.hse.group_project.nasvazi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.request.GetAvailableTableById
import ru.hse.group_project.nasvazi.model.request.GetAvailableTablesRequest
import ru.hse.group_project.nasvazi.model.response.GetAllTablesResponse
import ru.hse.group_project.nasvazi.model.response.GetAvailableTablesResponse
import ru.hse.group_project.nasvazi.model.response.GetUnavailableTablesResponse
import ru.hse.group_project.nasvazi.service.TableService

@RequestMapping("/table")
@RestController
@CrossOrigin
class TableController(
    private val tableService: TableService
) {
    @PostMapping("/unavailable")
    fun getUnavailableTables(request: GetAvailableTablesRequest): GetUnavailableTablesResponse {
        return GetUnavailableTablesResponse(tableService.getUnavailable(request.date))
    }

    @Operation(summary = "Получает инфу по свободным столикам по дате и количеству человек")
    @PostMapping("/available")
    fun getAvailableTables(@RequestBody request: GetAvailableTablesRequest): GetAvailableTablesResponse {
        return GetAvailableTablesResponse(tableService.getAvailable(request.date, capacity = request.capacity))
    }

    @Operation(summary = "Получает инфу по свободному времени для столика")
    @PostMapping("/available-by-table")
    fun getAvailableTableById(@RequestBody request: GetAvailableTableById): GetAvailableTablesResponse {
        return GetAvailableTablesResponse(tableService.getAvailableById(request.date, tableId = request.tableId))
    }

    @Operation(summary = "Возвращает информацию о всех столах в заведении")
    @GetMapping("/all")
    fun getAllTables(): GetAllTablesResponse {
        return GetAllTablesResponse(tableService.getAll())
    }
}
