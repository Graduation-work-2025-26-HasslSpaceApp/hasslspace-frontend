package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.UserDto

data class UserExpandedInfo(
    val id: String,
    val username: String,
    val nickname: String,
    val status: String,
    val email: String,
    val avatarUrl: String?,
    val description: String
)

fun UserDto.toDomain(): UserExpandedInfo {
    return UserExpandedInfo(
        id = this.id,
        username = this.name,
        nickname = this.nickname,
        status = this.status,
        email = this.email,
        avatarUrl = this.photoURL,
        description = this.description ?: ""
    )
}