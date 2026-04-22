package ru.hse.app.androidApp.domain.usecase.chats

import ru.hse.app.androidApp.domain.repository.ChatRepository
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
        ) // todo
    }
}