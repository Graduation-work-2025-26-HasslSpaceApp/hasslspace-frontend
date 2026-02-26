package ru.hse.app.androidApp.domain.usecase.servers

import ru.hse.app.androidApp.domain.model.entity.Invitation
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class GetServerInvitationsUseCase @Inject constructor(
    private val serverRepository: ServerRepository
) {
    suspend operator fun invoke(serverId: String): Result<List<Invitation>> {
        return serverRepository.getServerInvitations(serverId)
    }
}