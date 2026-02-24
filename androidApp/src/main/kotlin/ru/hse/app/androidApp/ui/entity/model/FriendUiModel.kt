package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.Type
import ru.hse.app.androidApp.domain.model.entity.UserInfo

@Immutable
data class FriendUiModel(
    val id: String,
    val name: String,
    val nickname: String,
    val status: StatusPresentation,
    val avatarUrl: String,
    val type: TypeUiModel
)

fun UserInfo.toUi(): FriendUiModel {
    return FriendUiModel(
        id = this.id,
        name = this.name,
        nickname = this.nickname,
        status = this.status.toStatusPresentation(),
        avatarUrl = this.photoUrl ?: "",
        type = this.type.toUi()
    )
}

fun Type.toUi(): TypeUiModel {
    return when (this) {
        Type.FRIEND -> TypeUiModel.FRIEND
        Type.OUTGOING_REQUEST -> TypeUiModel.OUTGOING_REQUEST
        Type.INCOMING_REQUEST -> TypeUiModel.INCOMING_REQUEST
    }
}