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
@Table(name = "role")
class RoleEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence_generator")
    @GenericGenerator(
        name = "role_sequence_generator",
        strategy = "ru.hse.group_project.nasvazi.util.ResettableSequenceStyleGenerator",
        parameters = [Parameter(name = "sequence_name", value = "role_id_seq")]
    )
    var id: Long? = null

    @Column(name = "name")
    var name: String? = null
}
