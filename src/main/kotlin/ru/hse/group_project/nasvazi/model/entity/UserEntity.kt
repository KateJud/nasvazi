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
@Table(name = "user")
class UserEntity : UpdatablePersistentEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence_generator")
    @GenericGenerator(
        name = "user_sequence_generator",
        strategy = "ru.hse.group_project.nasvazi.util.ResettableSequenceStyleGenerator",
        parameters = [Parameter(name = "sequence_name", value = "user_id_seq")]
    )
    var id: Long? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "phone")
    var phone: String? = null

    @Column(name = "bonus")
    var bonus: Long = 0

    @Column(name = "chat_id")
    var chatId: Long = 0
}
