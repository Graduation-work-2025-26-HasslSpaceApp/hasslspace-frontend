package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.CreateServerDto

data class CreateServer(
    val name: String,
    val photoUrl: String? = null,
)

fun CreateServer.toDto(): CreateServerDto {
    return CreateServerDto(
        name = this.name,
        iconUrl = this.photoUrl
    )
}