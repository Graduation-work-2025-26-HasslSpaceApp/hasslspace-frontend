package ru.hse.app.androidApp.ui.entity.model

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.ServerInfo

@Immutable
data class ServerShortUiModel(
    val id: String,
    val name: String,
//    val participantCount: Int,
    val avatarUrl: String
)

fun ServerInfo.toUi(): ServerShortUiModel {
    return ServerShortUiModel(
        id = this.id,
        name = this.title,
//        participantCount = this.usersCount,
        avatarUrl = this.photoUrl ?: ""
    )
}