package ru.hse.group_project.nasvazi.model.entity

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.UpdateTimestamp
import ru.hse.group_project.nasvazi.model.entity.base.PersistentEntity
import java.time.LocalDateTime

@MappedSuperclass
abstract class UpdatablePersistentEntity : PersistentEntity() {

    @UpdateTimestamp
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
}
