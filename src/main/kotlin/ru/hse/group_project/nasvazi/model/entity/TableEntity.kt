package ru.hse.group_project.nasvazi.model.entity

data class TableEntity(

    var id: Long? = null,

    // unique
    // @Column(name = "name")
    var name: String? = null,

    // @Column(name = "capacity")
    var capacity: Long? = null,
    )
