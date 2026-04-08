package ru.hse.app.androidApp.data.repository

import ru.hse.app.androidApp.data.model.NewMessageDto
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.data.roomstorage.ChatDao
import ru.hse.app.androidApp.data.roomstorage.MessageEntity
import ru.hse.app.androidApp.domain.model.entity.ChatInfo
import ru.hse.app.androidApp.domain.model.entity.Message
import ru.hse.app.androidApp.domain.model.entity.toDomain
import ru.hse.app.androidApp.domain.repository.ChatRepository
import java.time.LocalDateTime
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller,
    private val chatDao: ChatDao
) : ChatRepository {

    override suspend fun saveMessageToRoom(
        id: String,
        chatId: String,
        userId: String,
        content: String,
        createdAt: LocalDateTime,
        isRead: Boolean
    ): Result<Unit> {
        return runCatching {
            chatDao.insertMessage(MessageEntity(id, chatId, userId, content, createdAt, isRead))
        }
    }

    override suspend fun sendMessage(
        chatId: String,
        content: String
    ): Result<String> {
        return apiCaller.safeApiCall {
            apiService.sendNewMessage(
                chatId,
                NewMessageDto(
                    content = content,
                    createdAt = LocalDateTime.now()
                )
            )
        }
    }

    override suspend fun getChatMessagesFromRoom(chatId: String): Result<List<Message>> {
        return runCatching {
            chatDao.getMessagesByChatId(chatId).map { it.toDomain() }
        }
    }

    override suspend fun getChatMessagesFromServer(
        chatId: String,
        lastMessageId: String?
    ): Result<List<Message>> {
        return apiCaller.safeApiCall { apiService.getMessageHistory(chatId, lastMessageId) }.mapCatching { messages ->
            messages.map { it.toDomain() }
        }
    }

    override suspend fun startChat(userId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.startChat(userId) }
    }

    override suspend fun getPrivateChats(): Result<List<ChatInfo>> {
        return apiCaller.safeApiCall { apiService.getPrivateChats() }.mapCatching { chats ->
            chats.map { it.toDomain() }
        }
    }

}
