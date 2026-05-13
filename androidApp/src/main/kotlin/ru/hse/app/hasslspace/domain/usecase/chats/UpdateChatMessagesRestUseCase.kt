package ru.hse.app.hasslspace.domain.usecase.chats

import ru.hse.app.hasslspace.domain.repository.ChatRepository
import java.time.LocalDateTime
import javax.inject.Inject

class UpdateChatMessagesRestUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String) {
        chatRepository.getChatMessagesFromServer(
            chatId,
            lastMessageId = null,
            fromDate = null,
            toDate = LocalDateTime.now(),
            limit = null
        )
    }
}