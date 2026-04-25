package ru.hse.app.hasslspace.domain.usecase.chats

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import ru.hse.app.hasslspace.data.roomstorage.ChatDao
import javax.inject.Inject

class ObserveAllUnreadCountsUseCase @Inject constructor(
    private val chatDao: ChatDao
) {
    operator fun invoke(chatIds: List<String>): Flow<Map<String, Int>> =
        combine(
            chatIds.map { chatId ->
                chatDao.observeUnreadCount(chatId)
                    .map { count -> chatId to count }
            }
        ) { pairs ->
            pairs.toMap()
        }
}