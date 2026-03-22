package ru.hse.app.androidApp.ui.entity.model.chats

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import java.time.LocalDateTime

sealed interface ChatUiState {
    data object Loading : ChatUiState
    data class Success(val data: ChatUiModel) : ChatUiState
    data class Error(val message: String) : ChatUiState
}

@Immutable
data class ChatUiModel(
    val id: String,
    val channelName: String,
    val channelMembers: List<ServerMemberUiModel>,
    val currentUser: ServerMemberUiModel,
    val messages: List<MessageUiModel>
)

@Immutable
data class MessageUiModel(
    val author: ServerMemberUiModel?,
    val content: String,
    val timestamp: LocalDateTime,
)