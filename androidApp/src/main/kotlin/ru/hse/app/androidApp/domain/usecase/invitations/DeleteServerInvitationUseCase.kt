package ru.hse.app.androidApp.domain.usecase.invitations

import ru.hse.app.androidApp.domain.repository.InvitationRepository
import javax.inject.Inject

class DeleteServerInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository
) {
    suspend operator fun invoke(invitationId: String): Result<String> {
        return invitationRepository.deleteServerInvitation(invitationId)
    }
}