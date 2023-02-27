package ru.hse.group_project.nasvazi.model.entity.base

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import ru.hse.group_project.nasvazi.model.entity.base.BaseEntity
import java.time.LocalDateTime

@MappedSuperclass
abstract class PersistentEntity : BaseEntity() {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime? = null
}
