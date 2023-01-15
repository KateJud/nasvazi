package ru.hse.group_project.nasvazi.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

fun Instant.toLocalDateTimeUTC(): LocalDateTime = this.toLocalDateTime(ZoneOffset.UTC)
fun Instant.toLocalDateTime(zoneId: ZoneId): LocalDateTime = LocalDateTime.ofInstant(this, zoneId)
