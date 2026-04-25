package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.TypeDto
import ru.hse.app.hasslspace.data.model.UserInfoDto

data class UserInfo(
    val id: String,
    val name: String,
    val nickname: String,
    val status: String,
    val photoUrl: String?,
    val type: Type,
)

fun UserInfoDto.toDomain(): UserInfo {
    return UserInfo(
        id = this.id,
        name = this.name,
        nickname = this.nickname,
        status = this.status,
        photoUrl = this.photoURL,
        type = this.type.toDomain()
    )
}

fun TypeDto.toDomain(): Type {
    return when (this) {
        TypeDto.FRIEND -> Type.FRIEND
        TypeDto.OUTGOING_REQUEST -> Type.OUTGOING_REQUEST
        TypeDto.INCOMING_REQUEST -> Type.INCOMING_REQUEST
        TypeDto.BLOCKED -> Type.BLOCKED
        TypeDto.NONE -> Type.NONE
    }
}