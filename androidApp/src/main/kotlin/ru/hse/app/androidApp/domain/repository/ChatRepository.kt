package ru.hse.app.androidApp.domain.repository

import ru.hse.app.androidApp.domain.model.entity.ChatInfo
import ru.hse.app.androidApp.domain.model.entity.Message
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
    ): Result<List<Message>>

    suspend fun getChatMessagesFromServer(
        chatId: String,
        lastMessageId: String? = null,
    ): Result<List<Message>>

    suspend fun startChat(
        userId: String
    ): Result<String>

    suspend fun getPrivateChats(): Result<List<ChatInfo>>
}