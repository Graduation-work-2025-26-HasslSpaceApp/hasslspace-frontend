package ru.hse.app.hasslspace.domain.usecase.chats

import ru.hse.app.hasslspace.domain.repository.ChatRepository
import javax.inject.Inject

class StartChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        userId: String,
    ): Result<String> {
        return chatRepository.startChat(userId)
    }
}