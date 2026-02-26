package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class DeleteServerInvitationUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String, invitationId: String): Result<String> {
        return serverRepository.deleteServerInvitation(serverId, invitationId)
    }
}