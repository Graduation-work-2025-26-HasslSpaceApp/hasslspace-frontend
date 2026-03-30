package ru.hse.app.androidApp.domain.repository

import ru.hse.app.androidApp.domain.model.entity.Invitation

interface InvitationRepository {
    suspend fun createInvitation(serverId: String): Result<Invitation>
    suspend fun deleteServerInvitation(serverId: String, invitationId: String): Result<String>
    suspend fun getServerInvitations(serverId: String): Result<List<Invitation>>
}