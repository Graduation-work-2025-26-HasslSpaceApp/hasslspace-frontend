package ru.hse.app.androidApp.domain.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import ru.hse.app.androidApp.data.model.ServerInviteDto
import java.time.LocalDateTime

data class Invitation(
    val code: String,
    val serverId: String,
    val creatorId: String,
    val expiresAt: LocalDateTime,
    val serverName: String? = null,
    val creatorName: String? = null,
    val inviteUrl : String? = null
)

fun ServerInviteDto.toDomain(): Invitation {
    return Invitation(
        code = this.code,
        serverId = this.serverId,
        creatorId = this.creatorId,
        expiresAt = this.expiresAt,
        serverName = this.serverName,
        creatorName = this.creatorName,
        inviteUrl = this.inviteUrl
    )
}