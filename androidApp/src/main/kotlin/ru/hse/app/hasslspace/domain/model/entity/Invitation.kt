package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.ServerInviteDto
import java.time.LocalDateTime

data class Invitation(
    val code: String,
    val serverId: String,
    val creatorId: String,
    val expiresAt: LocalDateTime,
    val serverName: String? = null,
    val creatorName: String? = null,
    val inviteUrl: String? = null
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