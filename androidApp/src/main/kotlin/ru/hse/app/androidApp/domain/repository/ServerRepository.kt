package ru.hse.app.androidApp.domain.repository

import ru.hse.app.androidApp.domain.model.entity.ChannelInfo
import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.model.entity.CreateServer
import ru.hse.app.androidApp.domain.model.entity.Invitation
import ru.hse.app.androidApp.domain.model.entity.RoleInfo
import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.model.entity.ServerInfoExpanded
import ru.hse.app.androidApp.domain.model.entity.UserInfo

interface ServerRepository {
    suspend fun createServerRole(serverId: String, createRole: CreateRole): Result<String>
    suspend fun createServer(createServer: CreateServer): Result<String>
    suspend fun createChannel(serverId: String, createChannel: CreateChannel): Result<String>
    suspend fun createInvitation(serverId: String,): Result<Invitation>

    suspend fun deleteChannel(serverId: String, channelId: String): Result<String>
    suspend fun deleteServerInvitation(serverId: String, invitationId: String): Result<String>
    suspend fun deleteServerMember(serverId: String, memberId: String): Result<String>
    suspend fun deleteServer(serverId: String): Result<String>
    suspend fun deleteRole(serverId: String, roleId: String): Result<String>

    suspend fun getServers(): Result<List<ServerInfo>>
    suspend fun getServerInfo(serverId: String): Result<ServerInfoExpanded>
    suspend fun getServerInvitations(serverId: String): Result<List<Invitation>>
    suspend fun getServerRoles(serverId: String): Result<List<RoleInfo>>
//    suspend fun getServerUserRoles(userId: String, serverId: String): Result<List<RoleInfo>>
    suspend fun getFriendsNotInServer(serverId: String): Result<List<UserInfo>>
    suspend fun getChannelInfo(serverId: String, channelId: String): Result<ChannelInfo>

    suspend fun joinServer(code: String): Result<String>

    suspend fun updateServerOwner(userId: String, serverId: String): Result<String>
    suspend fun patchChannel(serverId: String, channelId: String, name: String?, position: Int?, maxMembers: Int?, isPrivate: Boolean?): Result<String>
    suspend fun patchServer(serverId: String, name: String?, photoUrl: String?): Result<String>
    suspend fun patchRole(serverId: String, roleId: String, name: String?, position: Int?, color: String?): Result<String>

    suspend fun assignRole(serverId: String, targetUserId: String, roleId: String): Result<String>
    suspend fun revokeRole(serverId: String, targetUserId: String, roleId: String): Result<String>

    suspend fun leaveServer(serverId: String): Result<String>
//    suspend fun sendServerInvitation(userId: String, serverId: String): Result<String>
}