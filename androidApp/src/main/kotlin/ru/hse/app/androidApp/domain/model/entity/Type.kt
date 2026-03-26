package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.TypeDto
import ru.hse.app.androidApp.data.model.UserInfoExtendedDto

enum class Type {
    FRIEND,
    INCOMING_REQUEST,
    OUTGOING_REQUEST,
    BLOCKED,
    NONE
}

fun UserInfoExtendedDto.StatusType.toDomain(): Type {
    return when (this) {
        UserInfoExtendedDto.StatusType.FRIEND -> Type.FRIEND
        UserInfoExtendedDto.StatusType.BLOCKED -> Type.BLOCKED
        UserInfoExtendedDto.StatusType.NONE -> Type.NONE
        UserInfoExtendedDto.StatusType.OUTGOING_REQUEST -> Type.OUTGOING_REQUEST
        UserInfoExtendedDto.StatusType.INCOMING_REQUEST -> Type.INCOMING_REQUEST
    }
}