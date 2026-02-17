package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.UserInfo

@Immutable
data class FriendUiModel(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String
)

fun UserInfo.toUi(): FriendUiModel {
    return FriendUiModel(
        id = this.id,
        name = this.name,
        nickname = this.nickname,
        status = this.status.toStatusPresentation(),
        avatarUrl = this.photoUrl?:""
    )
}