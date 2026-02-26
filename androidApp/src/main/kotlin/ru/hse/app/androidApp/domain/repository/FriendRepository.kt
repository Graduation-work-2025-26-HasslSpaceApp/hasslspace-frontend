package ru.hse.app.androidApp.domain.repository

import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfoWithStatus
import ru.hse.app.androidApp.domain.model.entity.UserInfo

interface FriendRepository {

    suspend fun getFriends(): Result<List<UserInfo>>
    suspend fun createFriendRequest(username: String): Result<String>
    suspend fun respondToFriendshipRequest(friendshipId: String, status: String): Result<String>
    suspend fun deleteFriendship(userId: String): Result<String>
    suspend fun getUserInfoExtended(userId: String): Result<UserExpandedInfoWithStatus>
    suspend fun getCommonServers(userId: String): Result<List<ServerInfo>>
}