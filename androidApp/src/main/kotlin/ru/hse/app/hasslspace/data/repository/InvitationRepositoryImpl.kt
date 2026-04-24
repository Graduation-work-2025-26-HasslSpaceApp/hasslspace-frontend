package ru.hse.app.hasslspace.data.repository

import ru.hse.app.hasslspace.data.network.ApiCaller
import ru.hse.app.hasslspace.data.network.ApiService
import ru.hse.app.hasslspace.domain.model.entity.Invitation
import ru.hse.app.hasslspace.domain.model.entity.toDomain
import ru.hse.app.hasslspace.domain.repository.InvitationRepository
import javax.inject.Inject

class InvitationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : InvitationRepository {

    override suspend fun createInvitation(serverId: String): Result<Invitation> {
        return apiCaller.safeApiCall { apiService.createInvitation(serverId) }.map { it.toDomain() }
    }

    override suspend fun deleteServerInvitation(
        invitationId: String
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteServerInvitation(invitationId) }
    }

    override suspend fun getServerInvitations(serverId: String): Result<List<Invitation>> {
        return apiCaller.safeApiCall { apiService.getServerInvitations(serverId) }
            .mapCatching { invitations ->
                invitations.map { it.toDomain() }
            }
    }
}
