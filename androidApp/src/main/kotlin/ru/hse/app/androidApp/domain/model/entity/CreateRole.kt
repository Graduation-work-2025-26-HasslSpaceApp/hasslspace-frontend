package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.CreateRoleDto

data class CreateRole(
    val name: String,
    val color: String,
    val members: List<String>,
)

fun CreateRole.toDto(): CreateRoleDto {
    return CreateRoleDto(
        name = this.name,
        color = this.color,
        members = this.members
    )
}