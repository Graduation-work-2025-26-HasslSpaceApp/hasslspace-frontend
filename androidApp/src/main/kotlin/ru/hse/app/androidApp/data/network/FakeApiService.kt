package ru.hse.app.androidApp.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import ru.hse.app.androidApp.data.model.UserDto

class FakeApiService : ApiService {

    private val fakeUser =
        UserDto("yuulkht", "testuser@example.com", "https://i.postimg.cc/J4DLnLCS/accountphoto.jpg")

    override suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        nickname: String
    ): Response<String> {
        return Response.success("mock_jwt_token")
    }

    override suspend fun loginUser(email: String, password: String): Response<String> {
        return Response.success("mock_jwt_token")
    }

    override suspend fun checkVerification(): Response<Boolean> {
        return Response.success(true)
    }

    override suspend fun sendVerificationCode(email: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun checkVerificationCode(verificationCode: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun getUserInfo(): Response<UserDto> {
        return Response.success(fakeUser)
    }

    override suspend fun saveUserPhoto(photoUrl: String): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        type: RequestBody,
        photoUrl: RequestBody?
    ): Response<String> {
        return Response.success("Данные успешно сохранены")
    }
}
