package ru.hse.group_project.nasvazi.model.entity

import ru.hse.group_project.nasvazi.model.enums.UserRole

data class RoleEntity(
    var id: Long? = null,
    // @Column(name = "name")
    var name: UserRole
)
