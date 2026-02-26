package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.ServerInfoDto

data class ServerInfo(
    val id: String,
    val title: String,
    val usersCount: Int,
    val photoUrl: String?,
)

fun ServerInfoDto.toDomain(): ServerInfo {
    return ServerInfo(
        id = this.id,
        title = this.name,
        usersCount = this.usersCount,
        photoUrl = this.photoURL
    )
}