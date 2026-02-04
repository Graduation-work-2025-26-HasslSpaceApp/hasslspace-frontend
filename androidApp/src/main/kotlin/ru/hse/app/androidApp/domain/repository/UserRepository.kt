package ru.hse.app.androidApp.domain.repository

import ru.hse.app.androidApp.domain.model.entity.UserInfo

interface UserRepository {

    suspend fun registerUser(email: String, password: String, username: String, nickname: String): Result<String>
    suspend fun loginUser(email: String, password: String): Result<String>
    suspend fun sendVerificationCode(email: String): Result<String>
    suspend fun checkVerificationCode(code: String): Result<String>
    suspend fun checkEmailVerification(): Result<String>
    suspend fun getUserInfo(): Result<UserInfo>

}