package ru.hse.app.androidApp.data.repository

import ru.hse.app.androidApp.data.model.UpdateChannelDto
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.domain.model.entity.ChannelInfo
import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.model.entity.RoleInfo
import ru.hse.app.androidApp.domain.model.entity.toDomain
import ru.hse.app.androidApp.domain.model.entity.toDto
import ru.hse.app.androidApp.domain.repository.ChannelRepository
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : ChannelRepository {
    override suspend fun createChannel(
        serverId: String,
        createChannel: CreateChannel
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.createChannel(serverId, createChannel.toDto()) }
    }

    override suspend fun deleteChannel(serverId: String, channelId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteChannel(serverId, channelId) }
    }

    override suspend fun getChannelInfo(
        serverId: String,
        channelId: String
    ): Result<ChannelInfo> {
        return apiCaller.safeApiCall { apiService.getChannelInfo(serverId, channelId) }
            .mapCatching { it.toDomain() }
    }

    override suspend fun patchChannel(
        serverId: String,
        channelId: String,
        name: String?,
        position: Int?,
        maxMembers: Int?,
        isPrivate: Boolean?
    ): Result<String> {
        val updateChannelDto = UpdateChannelDto(name, position, maxMembers, isPrivate)
        return apiCaller.safeApiCall {
            apiService.patchChannel(
                serverId,
                channelId,
                updateChannelDto
            )
        }
    }

    override suspend fun deleteChannelPermission(
        serverId: String,
        channelId: String,
        roleId: String
    ): Result<String> {
        return apiCaller.safeApiCall {
            apiService.deleteChannelPermission(
                serverId,
                channelId,
                roleId
            )
        }
    }

    override suspend fun assignChannelPermission(
        serverId: String,
        channelId: String,
        roleId: String
    ): Result<String> {
        return apiCaller.safeApiCall {
            apiService.assignChannelPermission(
                serverId,
                channelId,
                roleId
            )
        }
    }

    override suspend fun getChannelPermissions(
        serverId: String,
        channelId: String
    ): Result<List<RoleInfo>> {
        return apiCaller.safeApiCall {
            apiService.getChannelPermissions(
                serverId,
                channelId
            )
        }.mapCatching { roles -> roles.map { it.toDomain() } }

    }
}
