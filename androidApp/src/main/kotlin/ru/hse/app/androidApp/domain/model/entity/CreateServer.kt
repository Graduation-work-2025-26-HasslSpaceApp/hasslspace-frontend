package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.CreateServerDto

data class CreateServer(
    val name: String,
    val photoUrl: String? = null,
)

fun CreateServer.toDto(): CreateServerDto {
    return CreateServerDto(
        name = this.name,
        photoUrl = this.photoUrl
    )
}