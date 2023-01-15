package ru.hse.group_project.nasvazi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.hse.group_project.nasvazi.model.entity.BookingEntity

@Repository
interface BookingRepository  : JpaRepository<BookingEntity, Long> {
}
