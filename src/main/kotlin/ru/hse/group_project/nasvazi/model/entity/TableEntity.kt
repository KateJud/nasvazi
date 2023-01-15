package ru.hse.group_project.nasvazi.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter

@Entity
@Table(name = "table")
class TableEntity: UpdatablePersistentEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_sequence_generator")
    var id: Long? = null

    // todo unique
    @Column(name = "name")
    var name: String? = null

    // todo ограничения
    @Column(name = "capacity")
    var capacity: Long? = null

}
