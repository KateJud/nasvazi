package ru.hse.group_project.nasvazi.model.response

import ru.hse.group_project.nasvazi.model.dto.AnalysisBonusDto

data class AggregateBonusByUserResponse(
    val analysisBonusDtos: List<AnalysisBonusDto>
) : NasvaziResponse
