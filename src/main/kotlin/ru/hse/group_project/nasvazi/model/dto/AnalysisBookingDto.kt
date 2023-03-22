package ru.hse.group_project.nasvazi.model.dto

import java.time.LocalDate

data class AnalysisBookingDto(
    val date: LocalDate,
    val cancelled: Long,
    val confirmed: Long,
    val total: Long,
    val confirmedGuests: Long,
    val webQty: Long,
    val tgQty: Long,
    val androidQty: Long,
    val unknownPlatformQty: Long,
)
