package ru.hse.app.androidApp.ui.entity.model.chats

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.data.roomstorage.MessageEntity
import ru.hse.app.androidApp.domain.model.entity.ChatInfo
import ru.hse.app.androidApp.ui.entity.model.ChatShortUiModel
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel
import java.time.LocalDateTime

sealed interface ChatsUiState {
    data object Loading : ChatsUiState
    data class Success(val data: ChatsUiModel) : ChatsUiState
    data class Error(val message: String) : ChatsUiState
}

@Immutable
data class ChatsUiModel(
    val chats: List<ChatShortUiModel>,
    val friends: List<FriendUiModel>
)

@Immutable
data class MessageShortUiModel(
    val id: String,
    val content: String,
    val timestamp: LocalDateTime,
    val isRead: Boolean
)

fun ChatInfo.toChatShort(): ChatShortUiModel {
    return ChatShortUiModel(
        id = this.id,
        name = this.chatMembers
            .firstOrNull { !it.isCurrentUser }
            ?.name
            ?: "Личный чат",
        channelMembers = this.chatMembers.map { it.toUi() },
        messages = emptyList(),
        chatPhotoUrl = this.chatMembers
            .firstOrNull { !it.isCurrentUser }
            ?.photoURL
            ?: "",
        unreadCount = 0
    )
}

fun MessageEntity.toMessageShortUi(): MessageShortUiModel {
    return MessageShortUiModel(
        id = this.id,
        content = this.content ?: "",
        timestamp = this.createdAt,
        isRead = this.isRead
    )
}