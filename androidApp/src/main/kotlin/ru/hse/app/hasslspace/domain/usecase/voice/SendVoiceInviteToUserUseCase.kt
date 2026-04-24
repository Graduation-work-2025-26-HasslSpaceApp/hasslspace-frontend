package ru.hse.app.hasslspace.domain.usecase.voice

import ru.hse.app.hasslspace.domain.usecase.chats.SendMessageToUserUseCase
import javax.inject.Inject

class SendVoiceInviteToUserUseCase @Inject constructor(
    private val sendMessageToUserUseCase: SendMessageToUserUseCase,
) {
    suspend operator fun invoke(
        targetUserId: String,
        curUserName: String
    ): Result<String> {
        return sendMessageToUserUseCase(
            userId = targetUserId,
            content = "Пользователь $curUserName присоединился к личному звонку"
        )
    }
}