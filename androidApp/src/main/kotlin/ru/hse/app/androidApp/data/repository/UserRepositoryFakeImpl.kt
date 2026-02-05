package ru.hse.app.androidApp.data.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.hse.app.androidApp.domain.model.entity.UserInfo
import ru.hse.app.androidApp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryFakeImpl @Inject constructor() : UserRepository {

    override suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        nickname: String
    ): Result<String> {
        return Result.success("User registered successfully")
    }

    override suspend fun loginUser(
        email: String,
        password: String
    ): Result<String> {
        return Result.success("Login successful for $email")
    }

    override suspend fun sendVerificationCode(email: String): Result<String> {
        return Result.success("Verification code sent to $email")
    }

    override suspend fun checkVerificationCode(code: String): Result<String> {
        return Result.success("Verification code $code is valid")
    }

    override suspend fun checkEmailVerification(): Result<Boolean> {
        return Result.success(true)
    }

    override suspend fun getUserInfo(): Result<UserInfo> {
        val fakeUserInfo = UserInfo(
            email = "fakeuser@example.com",
            username = "fakeUser",
            avatarUrl = "https://opis-cdn.tinkoffjournal.ru/mercury/kak-koshka-manipuliruet-ludmi-1.gtqjybqvotxy..png"
        )
        return Result.success(fakeUserInfo)
    }

    override suspend fun saveUserPhoto(photoUrl: String): Result<String> {
        return Result.success("Photo saved successfully at $photoUrl")
    }

    override suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        type: RequestBody,
        photoUrl: RequestBody?
    ): Result<String> {
        return Result.success("Photo uploaded successfully")
    }
}
