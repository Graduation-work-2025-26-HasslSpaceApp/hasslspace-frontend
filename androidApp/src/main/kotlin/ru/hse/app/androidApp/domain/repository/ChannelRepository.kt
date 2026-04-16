package ru.hse.app.androidApp.domain.repository

import ru.hse.app.androidApp.domain.model.entity.ChannelInfo
import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.model.entity.RoleInfo

interface ChannelRepository {
    suspend fun createChannel(serverId: String, createChannel: CreateChannel): Result<String>
    suspend fun deleteChannel(serverId: String, channelId: String): Result<String>
    suspend fun getChannelInfo(serverId: String, channelId: String): Result<ChannelInfo>
    suspend fun patchChannel(
        serverId: String,
        channelId: String,
        name: String?,
        position: Int?,
        maxMembers: Int?,
        isPrivate: Boolean?
    ): Result<String>

    suspend fun deleteChannelPermission(
        serverId: String,
        channelId: String,
        roleId: String
    ): Result<String>

    suspend fun assignChannelPermission(
        serverId: String,
        channelId: String,
        roleId: String
    ): Result<String>

    suspend fun getChannelPermissions(
        serverId: String,
        channelId: String
    ): Result<List<RoleInfo>>
}