package ru.hse.app.hasslspace.ui.entity.model

import androidx.compose.runtime.Immutable
import ru.hse.app.hasslspace.ui.entity.model.chats.ChatMemberUiModel
import ru.hse.app.hasslspace.ui.entity.model.chats.MessageShortUiModel

@Immutable
data class ChatShortUiModel(
    val id: String,
    val name: String,
    val channelMembers: List<ChatMemberUiModel>,
    val messages: List<MessageShortUiModel>,
    val chatPhotoUrl: String,
    val unreadCount: Int
)