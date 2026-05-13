package ru.hse.app.hasslspace.data.network

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
import ru.hse.app.hasslspace.data.model.ChannelInfoDto
import ru.hse.app.hasslspace.data.model.ChatInfoDto
import ru.hse.app.hasslspace.data.model.CreateChannelDto
import ru.hse.app.hasslspace.data.model.CreateRoleDto
import ru.hse.app.hasslspace.data.model.CreateServerDto
import ru.hse.app.hasslspace.data.model.MessageDto
import ru.hse.app.hasslspace.data.model.NewMessageDto
import ru.hse.app.hasslspace.data.model.RegisterUserDto
import ru.hse.app.hasslspace.data.model.RoleInfoDto
import ru.hse.app.hasslspace.data.model.ServerInfoDto
import ru.hse.app.hasslspace.data.model.ServerInfoExpandedDto
import ru.hse.app.hasslspace.data.model.ServerInviteDto
import ru.hse.app.hasslspace.data.model.TokenRequest
import ru.hse.app.hasslspace.data.model.UpdateChannelDto
import ru.hse.app.hasslspace.data.model.UpdateProfileDto
import ru.hse.app.hasslspace.data.model.UpdateRoleDto
import ru.hse.app.hasslspace.data.model.UpdateServerDto
import ru.hse.app.hasslspace.data.model.UserDto
import ru.hse.app.hasslspace.data.model.UserInfoDto
import ru.hse.app.hasslspace.data.model.UserInfoExtendedDto
import java.time.LocalDateTime

interface ApiService {

    // Зарегистрироваться
    @POST(USER_SERVICE_URL + REGISTER_URL)
    suspend fun registerUser(
        @Body registerUserDto: RegisterUserDto
    ): Response<String>

    // Войти
    @GET(USER_SERVICE_URL + LOGIN_URL)
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<String>

    // Проверить верификацию email
    @GET(USER_SERVICE_URL + CHECK_VERIFICATION)
    suspend fun checkVerification(): Response<Boolean>

    // Прислать код подтверждения на почту
    @POST(USER_SERVICE_URL + SEND_CODE)
    suspend fun sendVerificationCode(
        @Query("email") email: String
    ): Response<String>

    // Проверить код верификации email
    @POST(USER_SERVICE_URL + CHECK_VERIFICATION_CODE)
    suspend fun checkVerificationCode(
        @Query("code") verificationCode: String
    ): Response<String>

    // Получить информацию о текущем пользователе
    @GET(USER_SERVICE_URL + GET_USER_PROFILE)
    suspend fun getUserInfo(): Response<UserDto>

    //Сохранение фото
    @Multipart
    @PUT(USER_SERVICE_URL + UPLOAD_USER_PHOTO)
    suspend fun uploadPhoto(
        @Part photo: MultipartBody.Part,
        @Part("type") type: RequestBody,
        @Part("photoUrl") photoUrl: RequestBody?
    ): Response<String>

    //Сохранение фото сервера
    @Multipart
    @PUT(SERVER_SERVICE_URL + UPLOAD_SERVER_PHOTO)
    suspend fun uploadServerPhoto(
        @Part photo: MultipartBody.Part,
        @Part("photoUrl") photoUrl: RequestBody?
    ): Response<String>

    // Обновить профиль пользователя
    @PATCH(USER_SERVICE_URL + UPDATE_PROFILE)
    suspend fun updateUserProfile(
        @Body updateProfileDto: UpdateProfileDto
    ): Response<String>

    // Изменить статус пользователя
    @PATCH(USER_SERVICE_URL + SET_USER_STATUS)
    suspend fun changeStatus(
        @Query("status") status: String
    ): Response<String>

    // Получить список друзей и заявок в друзья пользователя
    @GET(USER_SERVICE_URL + FRIENDS_LIST)
    suspend fun getFriends(): Response<List<UserInfoDto>>

    // Создать заявку в друзья
    @POST(USER_SERVICE_URL + SEND_FRIEND_REQUEST)
    suspend fun createFriendRequest(
        @Query("username") username: String,
    ): Response<String>

    // Ответить на заявку в друзья
    @PATCH(USER_SERVICE_URL + RESPOND_TO_FRIEND_REQUEST)
    suspend fun respondToFriendshipRequest(
        @Query("userId") userId: String,
        @Query("status") status: String,
    ): Response<String>

    // Удалить из друзей / отозвать приглащение в друзья
    @DELETE(USER_SERVICE_URL + REMOVE_FROM_FRIENDS)
    suspend fun deleteFriendship(
        @Query("userId") userId: String,
    ): Response<String>

    // Получить полную информацию о пользователе
    @GET(USER_SERVICE_URL + GET_USER_PROFILE_BY_ID)
    suspend fun getUserInfoExtended(
        @Query("userId") userId: String,
    ): Response<UserInfoExtendedDto>

    // Получить список серверов пользователя
    @GET(SERVER_SERVICE_URL + GET_SERVERS_LIST_URL)
    suspend fun getServers(): Response<List<ServerInfoDto>>

    // Получить список общих серверов пользователя с другим пользователем
    @GET(SERVER_SERVICE_URL + GET_SERVER_URL)
    suspend fun getCommonServers(
        @Query("friendId") userId: String,
    ): Response<List<ServerInfoDto>>

    // Создать роль на сервере
    @POST(SERVER_SERVICE_URL + CREATE_ROLE_URL)
    suspend fun createServerRole(
        @Query("serverId") serverId: String,
        @Body createRoleDto: CreateRoleDto
    ): Response<String>

    // Создать сервер
    @POST(SERVER_SERVICE_URL + CREATE_SERVER_URL)
    suspend fun createServer(
        @Body createServerDto: CreateServerDto
    ): Response<String>

    // Создать канал на сервере
    @POST(SERVER_SERVICE_URL + CREATE_CHANNEL_URL)
    suspend fun createChannel(
        @Query("serverId") serverId: String,
        @Body createChannelDto: CreateChannelDto
    ): Response<String>

    // Удалить канал на сервере
    @DELETE(SERVER_SERVICE_URL + DELETE_CHANNEL_URL)
    suspend fun deleteChannel(
        @Query("serverId") serverId: String,
        @Query("channelId") channelId: String
    ): Response<String>

    // Удалить приглашение на сервер
    @DELETE(SERVER_SERVICE_URL + DELETE_INVITE_URL)
    suspend fun deleteServerInvitation(
        @Query("inviteCode") inviteCode: String
    ): Response<String>

    // Удалить сервер
    @DELETE(SERVER_SERVICE_URL + DELETE_SERVER_URL)
    suspend fun deleteServer(
        @Query("serverId") serverId: String,
    ): Response<String>


    // Удалить роль
    @DELETE(SERVER_SERVICE_URL + DELETE_ROLE_URL)
    suspend fun deleteRole(
        @Query("serverId") serverId: String,
        @Query("roleId") roleId: String,
    ): Response<String>

    // Назначить роль
    @POST(SERVER_SERVICE_URL + ASSIGN_ROLE_URL)
    suspend fun assignRole(
        @Query("serverId") serverId: String,
        @Query("targetUserId") targetUserId: String,
        @Query("roleId") roleId: String,
    ): Response<String>

    // Забрать роль
    @DELETE(SERVER_SERVICE_URL + REVOKE_ROLE_URL)
    suspend fun revokeRole(
        @Query("serverId") serverId: String,
        @Query("targetUserId") targetUserId: String,
        @Query("roleId") roleId: String,
    ): Response<String>

    // Получить информацию о сервере
    @GET(SERVER_SERVICE_URL + GET_SERVER_URL)
    suspend fun getServerInfo(
        @Query("serverId") serverId: String,
    ): Response<ServerInfoExpandedDto>


    // Создать приглашение на сервер
    @POST(SERVER_SERVICE_URL + CREATE_INVITE_URL)
    suspend fun createInvitation(
        @Query("serverId") serverId: String,
    ): Response<ServerInviteDto>

    // Получить приглашения на сервер
    @GET(SERVER_SERVICE_URL + GET_ACTIVE_INVITES_URL)
    suspend fun getServerInvitations(
        @Query("serverId") serverId: String,
    ): Response<List<ServerInviteDto>>

    @GET(SERVER_SERVICE_URL + GET_FRIENDS_NOT_IN_SERVER_URL)
    suspend fun getFriendsNotInServer(
        @Query("serverId") serverId: String,
    ): Response<List<UserInfoDto>>

    // Получить роли на сервере
    @GET(SERVER_SERVICE_URL + GET_ROLES_URL)
    suspend fun getServerRoles(
        @Query("serverId") serverId: String,
    ): Response<List<RoleInfoDto>>

    // Присоединиться к серверу
    @POST(SERVER_SERVICE_URL + JOIN_SERVER_URL)
    suspend fun joinServer(
        @Query("code") code: String
    ): Response<String>

    // Покинуть сервер
    @DELETE(SERVER_SERVICE_URL + LEAVE_SERVER_URL)
    suspend fun leaveServer(
        @Query("serverId") serverId: String,
    ): Response<String>

    @DELETE(SERVER_SERVICE_URL + KICK_MEMBER_URL)
    suspend fun deleteServerMember(
        @Query("serverId") serverId: String,
        @Query("targetUserId") targetUserId: String,
    ): Response<String>

    // Изменить канал
    @PATCH(SERVER_SERVICE_URL + UPDATE_CHANNEL_URL)
    suspend fun patchChannel(
        @Query("serverId") serverId: String,
        @Query("channelId") channelId: String,
        @Body updateChannelDto: UpdateChannelDto
    ): Response<String>

    // Передать права на канал
    @PATCH(SERVER_SERVICE_URL + UPDATE_SERVER_OWNER_URL)
    suspend fun updateServerOwner(
        @Query("newOwnerId") newOwnerId: String,
        @Query("serverId") serverId: String,
    ): Response<String>

    // Изменить настройки сервера
    @PATCH(SERVER_SERVICE_URL + UPDATE_SERVER_URL)
    suspend fun patchServer(
        @Query("serverId") serverId: String,
        @Body updateServerDto: UpdateServerDto
    ): Response<String>

    // Изменить роль
    @PATCH(SERVER_SERVICE_URL + UPDATE_ROLE_URL)
    suspend fun patchRole(
        @Query("serverId") serverId: String,
        @Query("roleId") roleId: String,
        @Body updateRoleDto: UpdateRoleDto
    ): Response<String>

    // Получить информацию о канале
    @GET(SERVER_SERVICE_URL + GET_CHANNEL_INFO_URL)
    suspend fun getChannelInfo(
        @Query("serverId") serverId: String,
        @Query("channelId") channelId: String,
    ): Response<ChannelInfoDto>

    // Удалить права роли на канал
    @DELETE(SERVER_SERVICE_URL + DELETE_CHANNEL_PERMISSION_URL)
    suspend fun deleteChannelPermission(
        @Query("serverId") serverId: String,
        @Query("channelId") channelId: String,
        @Query("roleId") roleId: String,
    ): Response<String>

    // Назначить права роли на канал
    @POST(SERVER_SERVICE_URL + ASSIGN_CHANNEL_PERMISSION_URL)
    suspend fun assignChannelPermission(
        @Query("serverId") serverId: String,
        @Query("channelId") channelId: String,
        @Query("roleId") roleId: String,
    ): Response<String>

    // Получить роли, которым доступен канал
    @GET(SERVER_SERVICE_URL + ASSIGN_CHANNEL_PERMISSION_URL)
    suspend fun getChannelPermissions(
        @Query("serverId") serverId: String,
        @Query("channelId") channelId: String,
    ): Response<List<RoleInfoDto>>

    @POST(VOICE_SERVICE_URL + GET_TOKEN_FOR_VOICE_ROOM)
    suspend fun getTokenForVoiceRoom(
        @Body tokenRequest: TokenRequest
    ): Response<String>

    @GET(CHAT_SERVICE_URL + GET_TOKEN_FOR_CENTRIFUGO)
    suspend fun getTokenForCentrifugo(): Response<String>

    // чаты
    @GET(CHAT_SERVICE_URL + GET_CHAT_URL)
    suspend fun getPrivateChats(): Response<List<ChatInfoDto>>

    @GET(CHAT_SERVICE_URL + GET_CHAT_URL)
    suspend fun getChat(
        @Query("chatId") chatId: String
    ): Response<ChatInfoDto>

    @GET(CHAT_SERVICE_URL + GET_MESSAGES_HISTORY_URL)
    suspend fun getMessageHistory(
        @Query("chatId") chatId: String,
        @Query("fromMessageId") fromMessageId: String?,
        @Query("fromDate") fromDate: LocalDateTime?,
        @Query("toDate") toDate: LocalDateTime? = null,
        @Query("limit") limit: Int?
    ): Response<List<MessageDto>>

    @POST(CHAT_SERVICE_URL + SEND_MESSAGE_URL)
    suspend fun sendNewMessage(
        @Query("chatId") chatId: String,
        @Body newMessageDto: NewMessageDto
    ): Response<String>

    @POST(CHAT_SERVICE_URL + START_CHAT_URL)
    suspend fun startChat(
        @Query("targetUserId") targetUserId: String
    ): Response<String>

    @POST(CHAT_SERVICE_URL + START_CHAT_URL)
    suspend fun startChatChannel(
        @Query("channelId") channelId: String
    ): Response<String>

    @Multipart
    @PUT(CHAT_SERVICE_URL + PUT_FILE_CHAT_URL)
    suspend fun uploadFileToChat(
        @Part file: MultipartBody.Part,
        @Part("photoUrl") photoUrl: RequestBody?,
        @Part("fileType") fileType: RequestBody
    ): Response<String>

}