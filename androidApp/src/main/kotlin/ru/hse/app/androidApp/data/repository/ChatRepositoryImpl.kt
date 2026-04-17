package ru.hse.app.androidApp.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.data.model.NewMessageDto
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.data.roomstorage.ChatDao
import ru.hse.app.androidApp.data.roomstorage.MessageEntity
import ru.hse.app.androidApp.domain.model.entity.ChatInfo
import ru.hse.app.androidApp.domain.model.entity.toDomain
import ru.hse.app.androidApp.domain.model.entity.toEntity
import ru.hse.app.androidApp.domain.repository.ChatRepository
import ru.hse.app.androidApp.data.centrifugo.CentrifugeService
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller,
    private val centrifugeService: CentrifugeService,
    private val chatDao: ChatDao,
    private val scope: CoroutineScope
) : ChatRepository {

    init {
        scope.launch {
            centrifugeService.incomingMessages.collect { message ->
                chatDao.insertMessage(message.data.toEntity())
            }
        }
    }

    override suspend fun saveMessageToRoom(
        id: String,
        chatId: String,
        userId: String,
        content: String,
        createdAt: LocalDateTime,
        isRead: Boolean
    ): Result<Unit> {
        return runCatching {
            chatDao.insertMessage(MessageEntity(id, chatId, userId, content, null, createdAt, isRead)) // todo fileUrl
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

    override suspend fun getChatMessagesFromRoom(chatId: String): Flow<List<MessageEntity>> {
        getChatMessagesFromServer(chatId)
        return chatDao.observeMessagesByChatId(chatId)
    }

    override suspend fun getChatMessagesFromServer(
        chatId: String,
        lastMessageId: String?
    ) {
//    ): Result<List<Message>> {
        apiCaller.safeApiCall { apiService.getMessageHistory(chatId, lastMessageId) }.mapCatching { messages ->
            messages.map {
                chatDao.insertMessage(it.toEntity())
            }
        }
    }

    override suspend fun startChat(userId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.startChat(userId) }
    }

    override suspend fun getPrivateChats(curUserId: String): Result<List<ChatInfo>> {
        return apiCaller.safeApiCall { apiService.getPrivateChats() }.mapCatching { chats ->
            chats.map { it.toDomain(curUserId) }
        }
    }

    override suspend fun markMessageAsRead(messageId: String) {
        chatDao.markMessageAsRead(messageId)
    }

    override suspend fun markMessagesAsRead(chatId: String) {
        chatDao.markAllMessagesAsRead(chatId)
    }

    override suspend fun observeUnreadCount(chatId: String): Flow<Int> {
        return chatDao.observeUnreadCount(chatId)
    }
}
