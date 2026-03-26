package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class SendServerInvitationUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(userId: String, serverId: String): Result<String> {
        return Result.success("true") //todo прописать логику отправки сообщения
    }
}