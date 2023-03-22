package ru.hse.group_project.nasvazi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.hse.group_project.nasvazi.model.request.GetAvailableTablesRequest
import ru.hse.group_project.nasvazi.model.response.GetAllTablesResponse
import ru.hse.group_project.nasvazi.model.response.GetAvailableTablesResponse
import ru.hse.group_project.nasvazi.model.response.GetUnavailableTablesResponse
import ru.hse.group_project.nasvazi.service.TableService
import java.time.LocalDate

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

    @Operation(summary = "Выдает инфу по свободным столикам по дате и количеству человек")
    @GetMapping("/available")
    fun getAvailableTables(
        @RequestParam("date") date: LocalDate,
        @RequestParam("capacity") capacity: Long
    ): GetAvailableTablesResponse {
        return GetAvailableTablesResponse(
            tableService.getAvailable(
                date, capacity = capacity
            )
        )
    }

    @Operation(summary = "Выдает инфу по свободному времени для столика")
    @GetMapping("/available-by-table")
    fun getAvailableTableById(
        @RequestParam("date") date: LocalDate,
        @RequestParam("tableId") tableId: Long
    ): GetAvailableTablesResponse {
        return GetAvailableTablesResponse(tableService.getAvailableById(date, tableId = tableId))
    }

    @Operation(summary = "Возвращает информацию о всех столах в заведении")
    @GetMapping("/all")
    fun getAllTables(): GetAllTablesResponse {
        return GetAllTablesResponse(tableService.getAll())
    }
}
