package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.UserInfo

@Immutable
data class FriendUiModelInvitation(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String,
    val sent: Boolean
)

fun UserInfo.toInvitationUi(): FriendUiModelInvitation {
    return FriendUiModelInvitation(
        id = this.id,
        name = this.name,
        nickname = this.nickname,
        status = this.status.toStatusPresentation(),
        avatarUrl = this.photoUrl ?: "",
        sent = false
    )
}