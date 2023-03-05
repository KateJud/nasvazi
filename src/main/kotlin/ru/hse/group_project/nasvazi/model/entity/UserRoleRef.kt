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
@Table(name = "user_role_ref")
class UserRoleRef
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_ref_sequence_generator")
    @GenericGenerator(
        name = "user_role_ref_sequence_generator",
        strategy = "ru.hse.group_project.nasvazi.util.ResettableSequenceStyleGenerator",
        parameters = [Parameter(name = "sequence_name", value = "user_role_ref_id_seq")]
    )
    var id: Long? = null

    @Column(name = "user_id")
    var userId: Long? = null

    @Column(name = "role_id")
    var roleId: Long? = null
}
