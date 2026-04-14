package ru.hse.app.androidApp.domain.usecase.chats

import kotlinx.coroutines.flow.Flow
import ru.hse.app.androidApp.domain.repository.ChatRepository
import javax.inject.Inject

class ObserveUnreadCountUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): Flow<Int> =
        chatRepository.observeUnreadCount(chatId)
}