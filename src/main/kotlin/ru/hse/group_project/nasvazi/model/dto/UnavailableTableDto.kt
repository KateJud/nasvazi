package ru.hse.group_project.nasvazi.model.dto

import java.time.LocalDateTime

data class UnavailableTableDto(
    val name: String,
    val capacity: Long,
    var unavailableStartTime: List<LocalDateTime>
)
