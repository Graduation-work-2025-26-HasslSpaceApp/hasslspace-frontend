package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.InvitationDto
import java.time.LocalDateTime

data class Invitation(
    val id: String,
    val userId: String,
    val name: String,
    val username: String,
    val status: String,
    val photoURL: String?,
    val expTime: LocalDateTime
)

fun InvitationDto.toDomain(): Invitation {
    return Invitation(
        id = this.id,
        userId = this.userId,
        name = this.name,
        username = this.username,
        status = this.status,
        photoURL = this.photoURL,
        expTime = this.expTime
    )
}