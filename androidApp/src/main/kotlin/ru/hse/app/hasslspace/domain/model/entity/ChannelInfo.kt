package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.ChannelInfoDto

data class ChannelInfo(
    val id: String,
    val name: String,
    val isPrivate: Boolean = false,
    val type: String = "TEXT",
    val limit: Int? = null,
)

fun ChannelInfoDto.toDomain(): ChannelInfo {
    return ChannelInfo(
        id = this.id,
        name = this.name,
        isPrivate = this.isPrivate,
        type = this.type,
        limit = this.maxMembers,
    )
}