package ru.hse.app.hasslspace.domain.repository

import ru.hse.app.hasslspace.domain.model.entity.Invitation

interface InvitationRepository {
    suspend fun createInvitation(serverId: String): Result<Invitation>
    suspend fun deleteServerInvitation(invitationId: String): Result<String>
    suspend fun getServerInvitations(serverId: String): Result<List<Invitation>>
}