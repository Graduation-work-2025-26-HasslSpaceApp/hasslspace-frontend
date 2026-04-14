package ru.hse.app.androidApp.domain.usecase.chats

import ru.hse.app.androidApp.domain.repository.ChatRepository
import javax.inject.Inject

class MarkMessageAsReadUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        messageId: String
    ): Result<Unit> {
        return runCatching {
            chatRepository.markMessageAsRead(messageId)
        }
    }
}