package ru.hse.app.androidApp.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import ru.hse.app.androidApp.data.model.ServerInfoDto
import ru.hse.app.androidApp.data.model.UserDto
import ru.hse.app.androidApp.data.model.UserInfoDto
import ru.hse.app.androidApp.data.model.UserInfoExtendedDto

interface ApiService {

    @POST(USER_BASE_PATH_URL + REGISTER_URL)
    suspend fun registerUser(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("username") username: String,
        @Query("nickname") nickname: String,
    ): Response<String>

    @GET(USER_BASE_PATH_URL + LOGIN_URL)
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<String>

    @GET(USER_BASE_PATH_URL + CHECK_VERIFIED_URL)
    suspend fun checkVerification(): Response<Boolean>

    @PUT(USER_BASE_PATH_URL + SEND_VERIFICATION_CODE_URL)
    suspend fun sendVerificationCode(
        @Query("email") email: String
    ): Response<String>

    @GET(USER_BASE_PATH_URL + CHECK_VERIFICATION_CODE_URL)
    suspend fun checkVerificationCode(
        @Query("verificationCode") verificationCode: String
    ): Response<String>

    @GET(USER_BASE_PATH_URL + GET_USER_INFO_URL)
    suspend fun getUserInfo(): Response<UserDto>

    // Сохранение фото пользователя
    @PUT(USER_BASE_PATH_URL + UPDATE_USER_PHOTO_URL)
    suspend fun saveUserPhoto(
        @Query("photoUrl") photoUrl: String,
    ): Response<String>

    //Сохранение фото
    @Multipart
    @PUT(PHOTO_BASE_PATH_URL + UPLOAD_PHOTO_URL)
    suspend fun uploadPhoto(
        @Part photo: MultipartBody.Part,
        @Part("type") type: RequestBody,
        @Part("photoUrl") photoUrl: RequestBody?
    ): Response<String>

    // Получить список друзей и заявок в друзья пользователя
    @GET("") //TODO
    suspend fun getFriends(): Response<List<UserInfoDto>>

    // Получить список серверов пользователя
    @GET("") //TODO
    suspend fun getServers(): Response<List<ServerInfoDto>>

    // Изменить имя пользователя
    @PATCH("") // todo
    suspend fun changeName(newName: String): Response<String>

    // Изменить статус пользователя
    @PATCH("") // todo
    suspend fun changeStatus(status: String): Response<String>

    // Изменить описание пользователя
    @PATCH("") // todo
    suspend fun changeDesc(desc: String): Response<String>

    @POST("") //TODO
    suspend fun createFriendRequest(
        @Query("username") username: String,
    ): Response<String>

    @PATCH("")//TODO
    suspend fun respondToFriendshipRequest(
        @Query("friendshipId") friendshipId: String,
        @Query("status") status: String,
    ): Response<String>

    @DELETE("")//TODO
    suspend fun deleteFriendship(
        @Query("userId") userId: String,
    ): Response<String>

    @GET("") //todo
    suspend fun getUserInfoExtended(
        @Query("userId") userId: String,
    ): Response<UserInfoExtendedDto>

    // Получить список общих серверов пользователя с другим пользователем
    @GET("") //TODO
    suspend fun getCommonServers(
        @Query("userId") userId: String,
    ): Response<List<ServerInfoDto>>
}