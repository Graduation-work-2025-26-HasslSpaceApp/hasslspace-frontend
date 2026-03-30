package ru.hse.app.androidApp.data.repository

import ru.hse.app.androidApp.data.model.UpdateServerDto
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.domain.model.entity.CreateServer
import ru.hse.app.androidApp.domain.model.entity.ServerInfo
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

    override suspend fun createServer(createServer: CreateServer): Result<String> {
        return apiCaller.safeApiCall { apiService.createServer(createServer.toDto()) }
    }

    override suspend fun deleteServerMember(serverId: String, memberId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteServerMember(serverId, memberId) }
    }

    override suspend fun deleteServer(serverId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteServer(serverId) }
    }

    override suspend fun getServers(): Result<List<ServerInfo>> {
        return apiCaller.safeApiCall { apiService.getServers() }.mapCatching { serverDtoList ->
            serverDtoList.map { it.toDomain() }
        }
    }

    override suspend fun getServerInfo(serverId: String): Result<ServerInfoExpanded> {
        return apiCaller.safeApiCall { apiService.getServerInfo(serverId) }
            .mapCatching { it.toDomain() }
    }

    override suspend fun getFriendsNotInServer(serverId: String): Result<List<UserInfo>> {
        return apiCaller.safeApiCall { apiService.getFriendsNotInServer(serverId) }
            .mapCatching { users ->
                users.map { it.toDomain() }
            }
    }

    override suspend fun joinServer(code: String): Result<String> {
        return apiCaller.safeApiCall { apiService.joinServer(code) }
    }

    override suspend fun leaveServer(serverId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.leaveServer(serverId) }
    }

    override suspend fun updateServerOwner(userId: String, serverId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.updateServerOwner(userId, serverId) }
    }

    override suspend fun patchServer(
        serverId: String,
        name: String?,
        photoUrl: String?
    ): Result<String> {
        val updateServerDto = UpdateServerDto(name, photoUrl)
        return apiCaller.safeApiCall { apiService.patchServer(serverId, updateServerDto) }
    }
}
