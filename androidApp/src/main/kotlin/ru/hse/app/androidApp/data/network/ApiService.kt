package ru.hse.app.androidApp.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import ru.hse.app.androidApp.data.model.ChannelInfoDto
import ru.hse.app.androidApp.data.model.CreateChannelDto
import ru.hse.app.androidApp.data.model.CreateRoleDto
import ru.hse.app.androidApp.data.model.CreateServerDto
import ru.hse.app.androidApp.data.model.InvitationDto
import ru.hse.app.androidApp.data.model.RoleInfoDto
import ru.hse.app.androidApp.data.model.ServerInfoDto
import ru.hse.app.androidApp.data.model.ServerInfoExpandedDto
import ru.hse.app.androidApp.data.model.UserDto
import ru.hse.app.androidApp.data.model.UserInfoDto
import ru.hse.app.androidApp.data.model.UserInfoExtendedDto

interface ApiService {

    // Зарегистрироваться
    @POST(USER_BASE_PATH_URL + REGISTER_URL)
    suspend fun registerUser(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("username") username: String,
        @Query("nickname") nickname: String,
    ): Response<String>

    // Войти
    @GET(USER_BASE_PATH_URL + LOGIN_URL)
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<String>

    // Проверить верификацию email
    @GET(USER_BASE_PATH_URL + CHECK_VERIFIED_URL)
    suspend fun checkVerification(): Response<Boolean>

    // Прислать код подтверждения на почту
    @PUT(USER_BASE_PATH_URL + SEND_VERIFICATION_CODE_URL)
    suspend fun sendVerificationCode(
        @Query("email") email: String
    ): Response<String>

    // Проверить код верификации email
    @GET(USER_BASE_PATH_URL + CHECK_VERIFICATION_CODE_URL)
    suspend fun checkVerificationCode(
        @Query("verificationCode") verificationCode: String
    ): Response<String>

    // Получить информацию о текущем пользователе
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

    // Создать заявку в друзья
    @POST("") //TODO
    suspend fun createFriendRequest(
        @Query("username") username: String,
    ): Response<String>

    // Ответить на заявку в друзья
    @PATCH("")//TODO
    suspend fun respondToFriendshipRequest(
        @Query("friendshipId") friendshipId: String,
        @Query("status") status: String,
    ): Response<String>

    // Удалить из друзей / отозвать приглащение в друзья
    @DELETE("")//TODO
    suspend fun deleteFriendship(
        @Query("userId") userId: String,
    ): Response<String>

    // Получить полную информацию о пользователе
    @GET("") //todo
    suspend fun getUserInfoExtended(
        @Query("userId") userId: String,
    ): Response<UserInfoExtendedDto>

    // Получить список общих серверов пользователя с другим пользователем
    @GET("") //TODO
    suspend fun getCommonServers(
        @Query("userId") userId: String,
    ): Response<List<ServerInfoDto>>

    // Создать роль на сервере
    @POST("") //TODO
    suspend fun createServerRole(
        @Query("serverId") serverId: String,
        @Body createRoleDto: CreateRoleDto
    ): Response<String>

    // Создать сервер
    @POST("") //TODO
    suspend fun createServer(
        @Body createServerDto: CreateServerDto
    ): Response<String>

    // Создать канал на сервере
    @POST("") //TODO
    suspend fun createChannel(
        @Query("serverId") serverId: String,
        @Body createChannelDto: CreateChannelDto
    ): Response<String>

    // Удалить канал на сервере
    @DELETE("") //TODO
    suspend fun deleteChannel(
        @Query("serverId") serverId: String,
        @Query("channelId") channelId: String
    ): Response<String>

    // Удалить приглашение на сервер
    @DELETE("") //TODO
    suspend fun deleteServerInvitation(
        @Query("serverId") serverId: String,
        @Query("invitationId") invitationId: String
    ): Response<String>

    // Удалить участника на сервере
    @DELETE("") //TODO
    suspend fun deleteServerMember(
        @Query("serverId") serverId: String,
        @Query("userId") userId: String
    ): Response<String>

    // Удалить сервер
    @DELETE("") //TODO
    suspend fun deleteServer(
        @Query("serverId") serverId: String,
    ): Response<String>

    // Получить информацию о сервере
    @GET("") //TODO
    suspend fun getServerInfo(
        @Query("serverId") serverId: String,
    ): Response<ServerInfoExpandedDto>

    // Получить приглашения на сервер
    @GET("") //TODO
    suspend fun getServerInvitations(
        @Query("serverId") serverId: String,
    ): Response<List<InvitationDto>>

    @GET("") //TODO
    suspend fun getFriendsNotInServer(
        @Query("serverId") serverId: String,
    ): Response<List<UserInfoDto>>

    // Получить роли на сервере
    @GET("") //TODO
    suspend fun getServerRoles(
        @Query("serverId") serverId: String,
    ): Response<List<RoleInfoDto>>

    // Получить роли пользователя на сервере
    @GET("") //TODO
    suspend fun getServerUserRoles(
        @Query("userId") userId: String,
        @Query("serverId") serverId: String,
    ): Response<List<RoleInfoDto>>

    // Присоединиться к серверу
    @POST("") //TODO
    suspend fun joinServer(): Response<String>

    // Изменить канал
    @PATCH("") //TODO
    suspend fun patchChannel(): Response<String>

    // Передать права на канал
    @PATCH("") //TODO
    suspend fun patchServerOwner(
        @Query("newOwnerId") userId: String,
        @Query("serverId") serverId: String,
    ): Response<String>

    // Изменить настройки сервера
    @PATCH("") //TODO
    suspend fun patchServer(): Response<String>

    // Изменить роль
    @PATCH("") //TODO
    suspend fun patchRole(): Response<String>

    // Отправить приглашение на сервер
    @POST("") //TODO
    suspend fun sendServerInvitation(
        @Query("userId") userId: String,
        @Query("serverId") serverId: String,
    ): Response<String>

    @GET("") //TODO
    suspend fun getChannelInfo(
        @Query("serverId") serverId: String,
        @Query("channelId") channelId: String,
    ): Response<ChannelInfoDto>
}