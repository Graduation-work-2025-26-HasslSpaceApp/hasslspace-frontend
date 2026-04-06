package ru.hse.app.androidApp.domain.usecase.chats

import kotlinx.coroutines.flow.Flow
import ru.hse.app.androidApp.data.roomstorage.ChatDao
import javax.inject.Inject

class ObserveUnreadCountUseCase @Inject constructor(
    private val chatDao: ChatDao
) {
    operator fun invoke(chatId: String): Flow<Int> =
        chatDao.observeUnreadCount(chatId)
}