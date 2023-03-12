package ru.hse.group_project.nasvazi.model.dto

import java.time.LocalDateTime

data class AvailableTableDto(
    val name: String,
    val capacity: Long,
    var availableStartTimes: List<LocalDateTime>
)
