package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.UserDto

data class UserInfo(
    val username: String,
    val email: String,
    val avatarUrl: String?
)

fun UserDto.toDomain(): UserInfo {
    return UserInfo(
        username = this.username,
        email = this.email,
        avatarUrl = this.photoURL
    )
}