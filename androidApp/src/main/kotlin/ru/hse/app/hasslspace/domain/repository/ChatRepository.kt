package ru.hse.app.hasslspace.domain.repository

import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.hse.app.hasslspace.data.roomstorage.MessageEntity
import ru.hse.app.hasslspace.domain.model.entity.ChatInfo
import java.time.LocalDateTime

interface ChatRepository {

    suspend fun saveMessageToRoom(
        id: String,
        chatId: String,
        userId: String,
        content: String,
        fileUrl: String?,
        createdAt: LocalDateTime,
        isRead: Boolean = false,
    ): Result<Unit>

    suspend fun sendMessage(
        chatId: String,
        content: String,
        fileUrl: String?
    ): Result<String>

    suspend fun getChatMessagesFromRoom(
        chatId: String,
    ): Flow<List<MessageEntity>>

    suspend fun getChatMessagesFromServer(
        chatId: String,
        lastMessageId: String?,
        fromDate: LocalDateTime?,
        toDate: LocalDateTime?,
        limit: Int?
    )//: Result<List<Message>>

    suspend fun uploadFile(
        file: MultipartBody.Part,
        photoUrl: RequestBody?,
        fileType: RequestBody
    ): Result<String>

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