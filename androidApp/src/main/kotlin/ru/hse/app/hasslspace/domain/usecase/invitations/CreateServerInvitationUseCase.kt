package ru.hse.app.hasslspace.domain.usecase.invitations

import ru.hse.app.hasslspace.domain.model.entity.Invitation
import ru.hse.app.hasslspace.domain.repository.InvitationRepository
import javax.inject.Inject

class CreateServerInvitationUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository
) {
    suspend operator fun invoke(serverId: String): Result<Invitation> {
        return invitationRepository.createInvitation(serverId)
    }
}