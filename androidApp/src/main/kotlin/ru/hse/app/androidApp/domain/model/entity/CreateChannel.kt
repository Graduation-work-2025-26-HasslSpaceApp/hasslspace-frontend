package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.CreateChannelDto

data class CreateChannel(
    val name: String,
    val isPrivate: Boolean = false,
    val type: String = "text",
    val limit: Int? = null,
)

fun CreateChannel.toDto(): CreateChannelDto {
    return CreateChannelDto(
        name = this.name,
        isPrivate = this.isPrivate,
        type = this.type,
        limit = this.limit,
    )
}