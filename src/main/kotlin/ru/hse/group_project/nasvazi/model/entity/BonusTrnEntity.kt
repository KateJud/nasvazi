package ru.hse.group_project.nasvazi.model.entity

import ru.hse.group_project.nasvazi.model.enums.TrnType
import java.time.LocalDateTime

data class BonusTrnEntity(
    val userId: Long,
    val qty: Long,
    val type: TrnType,
    val timeWhen: LocalDateTime,
)
