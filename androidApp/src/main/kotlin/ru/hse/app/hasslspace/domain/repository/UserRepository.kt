package ru.hse.app.hasslspace.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.hse.app.hasslspace.domain.model.entity.UserExpandedInfo

interface UserRepository {

    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        username: String
    ): Result<String>

    suspend fun loginUser(email: String, password: String): Result<String>
    suspend fun sendVerificationCode(email: String): Result<String>
    suspend fun checkVerificationCode(code: String): Result<String>
    suspend fun checkEmailVerification(): Result<Boolean>
    suspend fun getUserInfo(): Result<UserExpandedInfo>
    suspend fun saveUserPhoto(photoUrl: String): Result<String>

    suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        type: RequestBody,
        photoUrl: RequestBody?
    ): Result<String>

    suspend fun changeUserName(newName: String): Result<String>
    suspend fun changeUserStatus(status: String): Result<String>
    suspend fun changeUserDesc(desc: String): Result<String>
}