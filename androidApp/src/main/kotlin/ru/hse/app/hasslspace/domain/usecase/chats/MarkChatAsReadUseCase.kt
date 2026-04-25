package ru.hse.app.hasslspace.domain.usecase.chats

import ru.hse.app.hasslspace.domain.repository.ChatRepository
import javax.inject.Inject

class MarkChatAsReadUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        chatId: String
    ): Result<Unit> {
        return runCatching {
            chatRepository.markMessagesAsRead(chatId)
        }
    }
}