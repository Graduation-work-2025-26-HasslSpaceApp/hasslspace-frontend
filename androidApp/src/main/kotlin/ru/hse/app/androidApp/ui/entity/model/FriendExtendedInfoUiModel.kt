package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfoWithStatus

@Immutable
data class FriendExtendedInfoUiModel(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String,
    val description: String,
    val type: TypeUiModel,
)

fun UserExpandedInfoWithStatus.toUi(): FriendExtendedInfoUiModel {
    return FriendExtendedInfoUiModel(
        id = this.id,
        name = this.username,
        nickname = this.nickname,
        status = this.status.toStatusPresentation(),
        avatarUrl = this.avatarUrl ?: "",
        description = this.description,
        type = this.friendshipStatus.toUi(),
    )
}