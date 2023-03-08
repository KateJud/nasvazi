package ru.hse.group_project.nasvazi.model.dto

import java.time.Instant

data class UnavailableTableDto(
    val name: String,
    val capacity: Long,
    var unavailableStartTime: List<Instant>
)
