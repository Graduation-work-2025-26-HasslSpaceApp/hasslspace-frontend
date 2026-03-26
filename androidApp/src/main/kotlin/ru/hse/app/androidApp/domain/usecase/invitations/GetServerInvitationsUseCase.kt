package ru.hse.app.androidApp.domain.usecase.invitations

import ru.hse.app.androidApp.domain.model.entity.Invitation
import ru.hse.app.androidApp.domain.repository.InvitationRepository
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class GetServerInvitationsUseCase @Inject constructor(
    private val invitationRepository: InvitationRepository
) {
    suspend operator fun invoke(serverId: String): Result<List<Invitation>> {
        return invitationRepository.getServerInvitations(serverId)
    }
}