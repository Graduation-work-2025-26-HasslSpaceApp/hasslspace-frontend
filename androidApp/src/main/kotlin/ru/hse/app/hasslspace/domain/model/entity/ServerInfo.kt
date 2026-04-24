package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.ServerInfoDto

data class ServerInfo(
    val id: String,
    val title: String,
    val photoUrl: String?,
)

fun ServerInfoDto.toDomain(): ServerInfo {
    return ServerInfo(
        id = this.id,
        title = this.name,
        photoUrl = this.photoURL
    )
}