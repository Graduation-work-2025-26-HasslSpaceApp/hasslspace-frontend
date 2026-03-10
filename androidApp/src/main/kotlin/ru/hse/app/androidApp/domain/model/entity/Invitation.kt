package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.InvitationDto
import java.time.LocalDateTime

data class Invitation(
    val id: String,
    val link: String,
    val expTime: LocalDateTime
)

fun InvitationDto.toDomain(): Invitation {
    return Invitation(
        id = this.id,
        link = this.link,
        expTime = this.expTime
    )
}