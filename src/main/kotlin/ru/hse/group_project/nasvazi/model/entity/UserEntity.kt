package ru.hse.group_project.nasvazi.model.entity

data class UserEntity(

    var id: Long? = null,

    // @Column(name = "name")
    var name: String,

    // @Column(name = "phone")
    var phone: String,

    // @Column(name = "bonus")
    var bonus: Long = 0,

    // @Column(name = "chat_id")
    var chatId: Long = 0,
) {
    constructor(id: Long, user: UserEntity) : this(
        id = id,
        phone = user.phone,
        name = user.name,
    )
}
