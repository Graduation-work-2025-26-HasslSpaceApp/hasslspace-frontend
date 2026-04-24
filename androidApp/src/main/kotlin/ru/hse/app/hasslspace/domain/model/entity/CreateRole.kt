package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.CreateRoleDto

data class CreateRole(
    val name: String,
    val color: String,
)

fun CreateRole.toDto(): CreateRoleDto {
    return CreateRoleDto(
        name = this.name,
        color = this.color,
    )
}