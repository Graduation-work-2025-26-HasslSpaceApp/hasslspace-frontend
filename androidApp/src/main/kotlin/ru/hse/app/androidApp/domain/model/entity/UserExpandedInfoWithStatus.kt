package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.UserInfoExtendedDto

data class UserExpandedInfoWithStatus(
    val id: String,
    val username: String,
    val nickname: String,
    val status: String,
    val avatarUrl: String?,
    val description: String,
    val friendshipStatus: Type
)

fun UserInfoExtendedDto.toDomain(): UserExpandedInfoWithStatus {
    return UserExpandedInfoWithStatus(
        id = this.id,
        username = this.name,
        nickname = this.nickname,
        status = this.status,
        avatarUrl = this.photoURL,
        description = this.description?:"",
        friendshipStatus = this.friendshipStatus.toDomain()
    )
}