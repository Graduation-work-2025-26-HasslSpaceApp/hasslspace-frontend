package ru.hse.app.hasslspace.data.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.hse.app.hasslspace.data.model.UpdateServerDto
import ru.hse.app.hasslspace.data.network.ApiCaller
import ru.hse.app.hasslspace.data.network.ApiService
import ru.hse.app.hasslspace.domain.model.entity.CreateServer
import ru.hse.app.hasslspace.domain.model.entity.ServerInfo
import ru.hse.app.hasslspace.domain.model.entity.ServerInfoExpanded
import ru.hse.app.hasslspace.domain.model.entity.UserInfo
import ru.hse.app.hasslspace.domain.model.entity.toDomain
import ru.hse.app.hasslspace.domain.model.entity.toDto
import ru.hse.app.hasslspace.domain.repository.ServerRepository
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

    override suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        photoUrl: RequestBody?
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.uploadServerPhoto(photo, photoUrl) }
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
