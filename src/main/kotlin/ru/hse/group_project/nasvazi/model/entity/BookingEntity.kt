package ru.hse.group_project.nasvazi.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import ru.hse.group_project.nasvazi.model.enums.BookingStatus
import java.time.Instant

@Entity
@Table(name = "booking")
class BookingEntity : UpdatablePersistentEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_sequence_generator")
    @GenericGenerator(
        name = "booking_sequence_generator",
        strategy = "ru.hse.group_project.nasvazi.util.ResettableSequenceStyleGenerator",
        parameters = [Parameter(name = "sequence_name", value = "booking_id_seq")]
    )
    var id: Long? = null

    @Column(name = "user_id")
    var userId: Long? = null

    @Column(name = "table_id")
    var tableId: Long? = null

    @Column(name = "time_from")
    var timeFrom: Instant? = null

    @Column(name = "time_to")
    var timeTo: Instant? = null

    @Column(name = "participants")
    var participants: Long = 1L

    @Column(name = "status")
    var status: BookingStatus = BookingStatus.CREATED
}
