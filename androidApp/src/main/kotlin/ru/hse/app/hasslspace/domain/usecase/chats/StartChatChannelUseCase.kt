package ru.hse.app.hasslspace.domain.usecase.chats

import ru.hse.app.hasslspace.domain.repository.ChatRepository
import javax.inject.Inject

class StartChatChannelUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(
        channelId: String,
    ): Result<String> {
        return chatRepository.startChatChannel(channelId)
    }
}