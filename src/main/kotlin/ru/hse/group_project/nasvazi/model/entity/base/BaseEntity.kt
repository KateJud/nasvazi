package ru.hse.group_project.nasvazi.model.entity.base

import com.vladmihalcea.hibernate.type.array.LongArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import javax.persistence.MappedSuperclass

@TypeDefs(
    TypeDef(name = "long-array", typeClass = LongArrayType::class),
    TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType::class),
    TypeDef(name = "jsonb", typeClass = JsonBinaryType::class),
    TypeDef(name = "string-array", typeClass = StringArrayType::class),
)
@MappedSuperclass
abstract class BaseEntity
