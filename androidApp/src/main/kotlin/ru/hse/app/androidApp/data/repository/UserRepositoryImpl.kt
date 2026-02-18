package ru.hse.app.androidApp.data.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.hse.app.androidApp.data.network.ApiCaller
import ru.hse.app.androidApp.data.network.ApiService
import ru.hse.app.androidApp.domain.model.entity.ServerInfo
import ru.hse.app.androidApp.domain.model.entity.UserExpandedInfo
import ru.hse.app.androidApp.domain.model.entity.UserInfo
import ru.hse.app.androidApp.domain.model.entity.toDomain
import ru.hse.app.androidApp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiCaller: ApiCaller
) : UserRepository {

    override suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        nickname: String
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.registerUser(email, password, username, nickname) }
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.loginUser(email, password) }
    }

    override suspend fun sendVerificationCode(email: String): Result<String> {
        return apiCaller.safeApiCall { apiService.sendVerificationCode(email) }
    }

    override suspend fun checkVerificationCode(code: String): Result<String> {
        return apiCaller.safeApiCall { apiService.checkVerificationCode(code) }
    }

    override suspend fun checkEmailVerification(): Result<Boolean> {
        return apiCaller.safeApiCall { apiService.checkVerification() }
    }

    override suspend fun getUserInfo(): Result<UserExpandedInfo> {
        return apiCaller.safeApiCall { apiService.getUserInfo() }.mapCatching { userDto -> userDto.toDomain() }
    }

    override suspend fun saveUserPhoto(photoUrl: String): Result<String> {
        return apiCaller.safeApiCall { apiService.saveUserPhoto(photoUrl) }
    }

    override suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        type: RequestBody,
        photoUrl: RequestBody?
    ): Result<String> {
        return apiCaller.safeApiCall { apiService.uploadPhoto(photo, type, photoUrl) }
    }

    override suspend fun getFriends(): Result<List<UserInfo>> {
        return apiCaller.safeApiCall { apiService.getFriends() }.mapCatching { userDtoList ->
            userDtoList.map { it.toDomain() }
        }
    }

    override suspend fun getServers(): Result<List<ServerInfo>> {
        return apiCaller.safeApiCall { apiService.getServers() }.mapCatching { serverDtoList ->
            serverDtoList.map { it.toDomain() }
        }
    }

    override suspend fun changeUserName(newName: String): Result<String> {
        return apiCaller.safeApiCall { apiService.changeName(newName) }
    }

    override suspend fun changeUserStatus(status: String): Result<String> {
        return apiCaller.safeApiCall { apiService.changeStatus(status) }
    }

    override suspend fun changeUserDesc(desc: String): Result<String> {
        return apiCaller.safeApiCall { apiService.changeDesc(desc) }
    }
}
