package ru.hse.app.hasslspace.domain.usecase.chats

import ru.hse.app.hasslspace.domain.model.entity.ChatInfo
import ru.hse.app.hasslspace.domain.repository.ChatRepository
import javax.inject.Inject

class GetPrivateChatsUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(curUserId: String): Result<List<ChatInfo>> {
        return chatRepository.getPrivateChats(curUserId)
    }
}