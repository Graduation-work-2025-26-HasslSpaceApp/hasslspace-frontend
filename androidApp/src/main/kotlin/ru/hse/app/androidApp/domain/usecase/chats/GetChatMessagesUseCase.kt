package ru.hse.app.androidApp.domain.usecase.chats

import ru.hse.app.androidApp.domain.model.entity.Message
import ru.hse.app.androidApp.domain.repository.ChatRepository
import javax.inject.Inject

class GetChatMessagesUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): Result<List<Message>> {
        val roomResult = chatRepository.getChatMessagesFromRoom(chatId)
        val serverResult = chatRepository.getChatMessagesFromServer(
            chatId = chatId,
            lastMessageId = roomResult.getOrNull()?.lastOrNull()?.id
        )

        return when {
            roomResult.isFailure -> roomResult
            serverResult.isFailure -> serverResult
            else -> {
                val roomMessages = roomResult.getOrDefault(emptyList())
                val serverMessages = serverResult.getOrDefault(emptyList())

                for (message in serverMessages) {
                    chatRepository.saveMessageToRoom(
                        id = message.id,
                        chatId = message.chatId,
                        userId = message.userId,
                        content = message.content,
                        createdAt = message.createdAt,
                        isRead = message.isRead
                    )
                }

                val mergedMessages = (roomMessages + serverMessages)
                    .distinctBy { it.id }
                    .sortedBy { it.createdAt }

                Result.success(mergedMessages)
            }
        }
    }
}