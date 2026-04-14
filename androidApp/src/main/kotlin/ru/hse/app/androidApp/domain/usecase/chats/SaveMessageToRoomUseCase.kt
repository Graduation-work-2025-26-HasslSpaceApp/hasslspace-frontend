package ru.hse.app.androidApp.domain.usecase.chats

import ru.hse.app.androidApp.domain.repository.ChatRepository
import java.time.LocalDateTime
import javax.inject.Inject

class SaveMessageToRoomUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        id: String,
        chatId: String,
        userId: String,
        content: String,
        createdAt: LocalDateTime,
    ): Result<Unit> {
        return runCatching {
            chatRepository.saveMessageToRoom(id, chatId, userId, content, createdAt)
        }
    }
}