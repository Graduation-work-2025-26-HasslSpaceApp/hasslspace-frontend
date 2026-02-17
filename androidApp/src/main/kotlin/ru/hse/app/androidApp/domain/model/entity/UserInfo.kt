package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.UserInfoDto

data class UserInfo(
    val id: String,
    val name: String,
    val nickname: String,
    val status: String,
    val photoUrl: String?
)

fun UserInfoDto.toDomain(): UserInfo {
    return UserInfo(
        id = this.id,
        name = this.name,
        nickname = this.nickname,
        status = this.status,
        photoUrl = this.photoURL
    )
}