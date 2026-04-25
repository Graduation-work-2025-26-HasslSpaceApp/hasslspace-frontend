package ru.hse.app.hasslspace.domain.usecase.chats

import javax.inject.Inject

class SendMessageToUserUseCase @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val startChatUseCase: StartChatUseCase
) {
    suspend operator fun invoke(
        userId: String,
        content: String,
    ): Result<String> {
        val chatIdResult = startChatUseCase(userId)

        chatIdResult.fold(
            onSuccess = { chatId ->
                return sendMessageUseCase(
                    chatId = chatId,
                    content = content,
                    attachments = emptyList() // todo
                )
            },
            onFailure = {
                return Result.failure(it)
            }
        )
    }
}