package ru.hse.app.androidApp.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.hse.app.androidApp.domain.model.entity.CreateServer
import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.model.entity.ServerInfoExpanded
import ru.hse.app.androidApp.domain.model.entity.UserInfo

interface ServerRepository {
    suspend fun createServer(createServer: CreateServer): Result<String>

    suspend fun deleteServerMember(serverId: String, memberId: String): Result<String>
    suspend fun deleteServer(serverId: String): Result<String>

    suspend fun getServers(): Result<List<ServerInfo>>
    suspend fun getServerInfo(serverId: String): Result<ServerInfoExpanded>
    suspend fun getFriendsNotInServer(serverId: String): Result<List<UserInfo>>

    suspend fun joinServer(code: String): Result<String>

    suspend fun updateServerOwner(userId: String, serverId: String): Result<String>
    suspend fun patchServer(serverId: String, name: String?, photoUrl: String?): Result<String>

    suspend fun leaveServer(serverId: String): Result<String>

    suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        photoUrl: RequestBody?
    ): Result<String>

}