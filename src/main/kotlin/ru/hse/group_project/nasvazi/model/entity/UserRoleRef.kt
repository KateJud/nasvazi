package ru.hse.group_project.nasvazi.model.entity

data class UserRoleRef(
    var id: Long? = null,
    // @Column(name = "user_id")
    var userId: Long? = null,
    // @Column(name = "role_id")
    var roleId: Long? = null
)
