package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.Invitation
import java.time.LocalDateTime

@Immutable
data class InvitationUiModel(
    val code: String,
    val serverId: String,
    val creatorId: String,
    val expirationDate: LocalDateTime,
    val serverName: String? = null,
    val creatorName: String? = null,
    val link : String? = null
)

fun Invitation.toUi(): InvitationUiModel {
    return InvitationUiModel(
        code = this.code,
        serverId = this.serverId,
        creatorId = this.creatorId,
        link = this.inviteUrl,
        serverName = this.serverName,
        creatorName = this.creatorName,
        expirationDate = this.expiresAt
    )
}