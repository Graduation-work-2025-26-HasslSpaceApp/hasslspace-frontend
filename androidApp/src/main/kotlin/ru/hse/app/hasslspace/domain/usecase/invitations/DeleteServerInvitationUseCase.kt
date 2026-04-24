package ru.hse.app.hasslspace.domain.usecase.invitations

import ru.hse.app.hasslspace.domain.repository.InvitationRepository
import javax.inject.Inject

class DeleteServerInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository
) {
    suspend operator fun invoke(invitationId: String): Result<String> {
        return invitationRepository.deleteServerInvitation(invitationId)
    }
}