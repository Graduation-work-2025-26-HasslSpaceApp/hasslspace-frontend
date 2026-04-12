package ru.hse.app.androidApp.domain.usecase.chats

import ru.hse.app.androidApp.domain.model.entity.ChatInfo
import ru.hse.app.androidApp.domain.repository.ChatRepository
import javax.inject.Inject

class GetPrivateChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        chatId: String,
        content: String,
    ): Result<List<ChatInfo>> {
        return chatRepository.getPrivateChats()
    }
}