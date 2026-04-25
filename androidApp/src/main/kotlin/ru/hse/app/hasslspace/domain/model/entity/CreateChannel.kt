package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.CreateChannelDto

data class CreateChannel(
    val name: String,
    val isPrivate: Boolean = false,
    val type: String = "TEXT",
    val limit: Int? = null,
)

fun CreateChannel.toDto(): CreateChannelDto {
    return CreateChannelDto(
        name = this.name,
        isPrivate = this.isPrivate,
        type = this.type,
        maxMembers = this.limit,
    )
}