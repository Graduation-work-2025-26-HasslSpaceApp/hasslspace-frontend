package ru.hse.app.androidApp.domain.repository

import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.model.entity.CreateServer
import ru.hse.app.androidApp.domain.model.entity.Invitation
import ru.hse.app.androidApp.domain.model.entity.RoleInfo
import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.model.entity.ServerInfoExpanded
import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfoWithStatus
import ru.hse.app.androidApp.domain.model.entity.UserInfo

interface ServerRepository {
    suspend fun createServerRole(serverId: String, createRole: CreateRole): Result<String>
    suspend fun createServer(createServer: CreateServer): Result<String>
    suspend fun createChannel(serverId: String, createChannel: CreateChannel): Result<String>

    suspend fun deleteChannel(serverId: String, channelId: String): Result<String>
    suspend fun deleteServerInvitation(serverId: String, invitationId: String): Result<String>
    suspend fun deleteServerMember(serverId: String, memberId: String): Result<String>
    suspend fun deleteServer(serverId: String): Result<String>

    suspend fun getServerInfo(serverId: String): Result<ServerInfoExpanded>
    suspend fun getServerInvitations(serverId: String): Result<List<Invitation>>
    suspend fun getServerRoles(serverId: String): Result<List<RoleInfo>>
    suspend fun getServerUserRoles(userId: String, serverId: String): Result<List<RoleInfo>>

    suspend fun joinServer(): Result<String>

    suspend fun patchServerOwner(userId: String, serverId: String): Result<String>
    suspend fun sendServerInvitation(userId: String, serverId: String): Result<String>
}