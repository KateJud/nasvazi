package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.dto.AnalysisBookingDto

data class AggregateByDateResponse(
    val analysisBookingDtos: List<AnalysisBookingDto>
) : NasvaziResponse
