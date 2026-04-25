package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.RoleInfoDto

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
        color = this.color ?: "#1A0000FF",
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