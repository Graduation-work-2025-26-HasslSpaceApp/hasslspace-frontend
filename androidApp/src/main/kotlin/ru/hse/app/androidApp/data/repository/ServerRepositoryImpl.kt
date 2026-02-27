package ru.hse.app.androidApp.data.repository

import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.model.entity.CreateServer
import ru.hse.app.androidApp.domain.model.entity.Invitation
import ru.hse.app.androidApp.domain.model.entity.RoleInfo
import ru.hse.app.androidApp.domain.model.entity.ServerInfoExpanded
import ru.hse.app.androidApp.domain.model.entity.UserInfo
import ru.hse.app.androidApp.domain.model.entity.toDomain
import ru.hse.app.androidApp.domain.model.entity.toDto
import ru.hse.app.androidApp.domain.repository.ServerRepository
import javax.inject.Inject

class ServerRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : ServerRepository {

    override suspend fun createServerRole(
        serverId: String,
        createRole: CreateRole
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.createServerRole(serverId, createRole.toDto()) }
    }

    override suspend fun createServer(createServer: CreateServer): Result<String> {
        return apiCaller.safeApiCall { apiService.createServer(createServer.toDto()) }
    }

    override suspend fun createChannel(
        serverId: String,
        createChannel: CreateChannel
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.createChannel(serverId, createChannel.toDto()) }
    }

    override suspend fun deleteChannel(serverId: String, channelId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteChannel(serverId, channelId) }
    }

    override suspend fun deleteServerInvitation(
        serverId: String,
        invitationId: String
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteServerInvitation(serverId, invitationId) }
    }

    override suspend fun deleteServerMember(serverId: String, memberId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteServerMember(serverId, memberId) }
    }

    override suspend fun deleteServer(serverId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteServer(serverId) }
    }

    override suspend fun getServerInfo(serverId: String): Result<ServerInfoExpanded> {
        return apiCaller.safeApiCall { apiService.getServerInfo(serverId) }
            .mapCatching { it.toDomain() }
    }

    override suspend fun getServerInvitations(serverId: String): Result<List<Invitation>> {
        return apiCaller.safeApiCall { apiService.getServerInvitations(serverId) }
            .mapCatching { invitations ->
                invitations.map { it.toDomain() }
            }
    }

    override suspend fun getServerRoles(serverId: String): Result<List<RoleInfo>> {
        return apiCaller.safeApiCall { apiService.getServerRoles(serverId) }.mapCatching { roles ->
            roles.map { it.toDomain() }
        }
    }

    override suspend fun getFriendsNotInServer(serverId: String): Result<List<UserInfo>> {
        return apiCaller.safeApiCall { apiService.getFriendsNotInServer(serverId) }
            .mapCatching { users ->
                users.map { it.toDomain() }
            }
    }

    override suspend fun getServerUserRoles(
        userId: String,
        serverId: String
    ): Result<List<RoleInfo>> {
        return apiCaller.safeApiCall { apiService.getServerUserRoles(userId, serverId) }
            .mapCatching { roles ->
                roles.map { it.toDomain() }
            }
    }

    //TODO
    override suspend fun joinServer(): Result<String> {
        return apiCaller.safeApiCall { apiService.joinServer() }
    }

    override suspend fun patchServerOwner(userId: String, serverId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.patchServerOwner(userId, serverId) }
    }

    override suspend fun sendServerInvitation(userId: String, serverId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.sendServerInvitation(userId, serverId) }
    }
}
