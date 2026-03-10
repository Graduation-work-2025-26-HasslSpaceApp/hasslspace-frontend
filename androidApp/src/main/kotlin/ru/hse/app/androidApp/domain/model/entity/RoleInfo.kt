package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.RoleInfoDto

data class RoleInfo(
    val id: String,
    val name: String,
    val color: String,
    val members: List<RoleMember>,
) {
    data class RoleMember(
        val id: String,
        val name: String,
        val username: String,
        val status: String,
        val photoURL: String?,
    )
}

fun RoleInfoDto.toDomain(): RoleInfo {
    return RoleInfo(
        id = this.id,
        name = this.name,
        color = this.color,
        members = this.members.map { memberDto ->
            RoleInfo.RoleMember(
                id = memberDto.id,
                name = memberDto.name,
                username = memberDto.username,
                status = memberDto.status,
                photoURL = memberDto.photoURL
            )
        }
    )
}