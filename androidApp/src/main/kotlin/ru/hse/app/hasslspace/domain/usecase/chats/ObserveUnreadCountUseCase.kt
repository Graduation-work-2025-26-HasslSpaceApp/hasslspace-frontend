package ru.hse.app.hasslspace.domain.usecase.chats

import kotlinx.coroutines.flow.Flow
import ru.hse.app.hasslspace.domain.repository.ChatRepository
import javax.inject.Inject

class ObserveUnreadCountUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatId: String): Flow<Int> =
        chatRepository.observeUnreadCount(chatId)
}