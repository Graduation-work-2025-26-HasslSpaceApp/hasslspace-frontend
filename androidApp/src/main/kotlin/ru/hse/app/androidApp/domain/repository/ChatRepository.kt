package ru.hse.app.androidApp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.hse.app.androidApp.data.roomstorage.MessageEntity
import ru.hse.app.androidApp.domain.model.entity.ChatInfo
import java.time.LocalDateTime

interface ChatRepository {

    suspend fun saveMessageToRoom(
        id: String,
        chatId: String,
        userId: String,
        content: String,
        createdAt: LocalDateTime,
        isRead: Boolean = false,
    ): Result<Unit>

    suspend fun sendMessage(
        chatId: String,
        content: String,
    ): Result<String>

    suspend fun getChatMessagesFromRoom(
        chatId: String,
    ): Flow<List<MessageEntity>>

    suspend fun getChatMessagesFromServer(
        chatId: String,
        lastMessageId: String? = null,
    )//: Result<List<Message>>

    suspend fun startChat(
        userId: String
    ): Result<String>

    suspend fun startChatChannel(
        channelId: String
    ): Result<String>

    suspend fun getPrivateChats(curUserId: String): Result<List<ChatInfo>>

    suspend fun markMessageAsRead(messageId: String)

    suspend fun markMessagesAsRead(chatId: String)

    suspend fun observeUnreadCount(chatId: String): Flow<Int>
}