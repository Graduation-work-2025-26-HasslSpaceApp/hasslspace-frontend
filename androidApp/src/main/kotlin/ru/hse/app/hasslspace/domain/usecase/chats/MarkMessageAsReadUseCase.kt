package ru.hse.app.hasslspace.domain.usecase.chats

import ru.hse.app.hasslspace.domain.repository.ChatRepository
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