package ru.hse.app.androidApp.data.repository

import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfoWithStatus
import ru.hse.app.androidApp.domain.model.entity.UserInfo
import ru.hse.app.androidApp.domain.model.entity.toDomain
import ru.hse.app.androidApp.domain.repository.FriendRepository
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : FriendRepository {

    override suspend fun getFriends(): Result<List<UserInfo>> {
        return apiCaller.safeApiCall { apiService.getFriends() }.mapCatching { userDtoList ->
            userDtoList.map { it.toDomain() }
        }
    }

    override suspend fun createFriendRequest(username: String): Result<String> {
        return apiCaller.safeApiCall { apiService.createFriendRequest(username) }
    }

    override suspend fun respondToFriendshipRequest(
        userId: String,
        status: String
    ): Result<String> {
//        enum class FriendshipStatus {
//            PENDING,
//            ACCEPTED,
//            DECLINED,
//            BLOCKED
//        }
        return apiCaller.safeApiCall { apiService.respondToFriendshipRequest(userId, status) }
    }

    override suspend fun deleteFriendship(userId: String): Result<String> {
        return apiCaller.safeApiCall { apiService.deleteFriendship(userId) }
    }

    override suspend fun getUserInfoExtended(userId: String): Result<UserExpandedInfoWithStatus> {
        return apiCaller.safeApiCall { apiService.getUserInfoExtended(userId) }
            .mapCatching { it.toDomain() }
    }

    override suspend fun getCommonServers(userId: String): Result<List<ServerInfo>> {
        return apiCaller.safeApiCall { apiService.getCommonServers(userId) }
            .mapCatching { serverDtoList ->
                serverDtoList.map { it.toDomain() }
            }
    }
}
