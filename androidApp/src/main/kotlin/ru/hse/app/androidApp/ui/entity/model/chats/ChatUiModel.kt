package ru.hse.app.androidApp.ui.entity.model.chats

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.domain.model.entity.ChatInfo
import ru.hse.app.androidApp.domain.model.entity.Message
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.entity.model.toStatusPresentation
import java.time.LocalDateTime

sealed interface ChatUiState {
    data object Loading : ChatUiState
    data class Success(val data: ChatUiModel) : ChatUiState
    data class Error(val message: String) : ChatUiState
}

@Immutable
data class ChatUiModel(
    val id: String,
    val name: String,
    val channelMembers: List<ChatMemberUiModel>,
    val messages: List<MessageUiModel>
)

@Immutable
data class MessageUiModel(
    val id: String,
    val author: ChatMemberUiModel,
    val content: String,
    val timestamp: LocalDateTime,
    val isRead: Boolean
)

@Immutable
data class ChatMemberUiModel(
    val id: String,
    val name: String,
    val username: String,
    val status: StatusPresentation,
    val photoURL: String,
    val isCurrentUser: Boolean,
)

fun ChatInfo.toUiPrivate(): ChatUiModel {
    return ChatUiModel(
        id = this.id,
        name = this.chatMembers
            .firstOrNull { !it.isCurrentUser }
            ?.name
            ?: "Личный чат",
        channelMembers = this.chatMembers.map { it.toUi() },
        messages = emptyList()
    )
}

fun ChatInfo.ChatMember.toUi(): ChatMemberUiModel {
    return ChatMemberUiModel(
        id = this.id,
        name = this.name,
        username = this.username,
        status = this.status.toStatusPresentation(),
        photoURL = this.photoURL ?: "",
        isCurrentUser = this.isCurrentUser,
    )
}

fun Message.toUi(members: List<ChatMemberUiModel>): MessageUiModel {
    return MessageUiModel(
        id = this.id,
        author = members.find { it.id == this.userId }
            ?: ChatMemberUiModel(
                id = this.userId,
                name = "Unknown",
                username = "unknown",
                status = StatusPresentation.OFFLINE,
                photoURL = "",
                isCurrentUser = false
            ),
        content = this.content,
        timestamp = this.createdAt,
        isRead = this.isRead
    )
}