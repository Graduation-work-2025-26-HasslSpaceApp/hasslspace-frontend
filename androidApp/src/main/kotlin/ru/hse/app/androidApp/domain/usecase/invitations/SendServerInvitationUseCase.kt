package ru.hse.app.androidApp.domain.usecase.invitations

import ru.hse.app.androidApp.domain.repository.InvitationRepository
import javax.inject.Inject

class SendServerInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository
) {
    suspend operator fun invoke(userId: String, serverId: String): Result<String> {
        return Result.success("true") //todo прописать логику отправки сообщения
    }
}