package ru.hse.app.androidApp.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import ru.hse.app.androidApp.data.model.CreateChannelDto
import ru.hse.app.androidApp.data.model.CreateRoleDto
import ru.hse.app.androidApp.data.model.CreateServerDto
import ru.hse.app.androidApp.data.model.InvitationDto
import ru.hse.app.androidApp.data.model.RoleInfoDto
import ru.hse.app.androidApp.data.model.ServerInfoDto
import ru.hse.app.androidApp.data.model.ServerInfoExpandedDto
import ru.hse.app.androidApp.data.model.TypeDto
import ru.hse.app.androidApp.data.model.UserDto
import ru.hse.app.androidApp.data.model.UserInfoDto
import ru.hse.app.androidApp.data.model.UserInfoExtendedDto
import java.time.LocalDateTime

class FakeApiService : ApiService {

    private val fakeUser =
        UserDto(
            "Юлия Кухтина",
            "yuulkht",
            "testuser@example.com",
            "В сети",
            "https://i.postimg.cc/J4DLnLCS/accountphoto.jpg",
            "Описание профиля описание профиля описание профиля описание профиля"
        )

    private val fakeUserExtended =
        UserInfoExtendedDto(
            "1",
            "Юлия Кухтина",
            "yuulkht",
            "testuser@example.com",
            "В сети",
            "https://i.postimg.cc/J4DLnLCS/accountphoto.jpg",
            "Описание профиля описание профиля описание профиля описание профиля",
            TypeDto.FRIEND
        )

    private val fakeFriends = listOf(
        UserInfoDto(
            "1",
            "Алексей Иванов",
            "alex_ivanov",
            "В сети",
            "https://i.postimg.cc/1XfF8BZh/friend1.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "2",
            "Мария Петрова",
            "masha_petrov",
            "Невидимка",
            "https://i.postimg.cc/K8Jxt5wQ/friend2.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "3",
            "Ирина Смирнова",
            "irina_smirnov",
            "Не беспокоить",
            "https://i.postimg.cc/3RmDC39Y/friend3.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "4",
            "Дмитрий Козлов",
            "dmitry_kozlov",
            "Не активен",
            "https://i.postimg.cc/tgG6rqDh/friend4.jpg",
            TypeDto.OUTGOING_REQUEST
        ),
        UserInfoDto(
            "5",
            "Елена Федорова",
            "elena_fedorova",
            "В сети",
            "https://i.postimg.cc/MTf5d7Z8/friend5.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "6",
            "Иван Ребров",
            "ivan_rebrov",
            "Не беспокоить",
            "https://i.postimg.cc/fyzsbpdZ/friend6.jpg",
            TypeDto.INCOMING_REQUEST
        ),
        UserInfoDto(
            "7",
            "Татьяна Белова",
            "tatiana_belova",
            "Невидимка",
            "https://i.postimg.cc/9F0CjTjR/friend7.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "8",
            "Максим Сидоров",
            "maxim_sidorov",
            "В сети",
            "https://i.postimg.cc/7Z1vBGzF/friend8.jpg",
            TypeDto.OUTGOING_REQUEST
        ),
        UserInfoDto(
            "9",
            "Ольга Червонова",
            "olga_chervonova",
            "Не активен",
            "https://i.postimg.cc/Y2myr5x6/friend9.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "10",
            "Петр Лебедев",
            "peter_lebedev",
            "Невидимка",
            "https://i.postimg.cc/mkGYD5Kp/friend10.jpg",
            TypeDto.INCOMING_REQUEST
        )
    )

    private val fakeServers = listOf(
        ServerInfoDto("1", "Сервер 1", 123, "https://i.postimg.cc/2yzCRhY8/server1.jpg"),
        ServerInfoDto("2", "Сервер 2", 15, "https://i.postimg.cc/XJH1pcGs/server2.jpg"),
        ServerInfoDto("3", "Сервер 3", 10, null),
        ServerInfoDto("4", "Сервер 4", 2, "https://i.postimg.cc/mg9f6RYn/server4.jpg"),
        ServerInfoDto("5", "Сервер 5", 28, "https://i.postimg.cc/BZkQQH7X/server5.jpg"),
        ServerInfoDto("6", "Сервер 6", 500, "https://i.postimg.cc/vZq3ZyGG/server6.jpg"),
        ServerInfoDto("7", "Сервер 7", 200, null),
        ServerInfoDto("8", "Сервер 8", 850, "https://i.postimg.cc/mtJ7VHTN/server8.jpg"),
        ServerInfoDto("9", "Сервер 9", 50, "https://i.postimg.cc/8C3bbFGG/server9.jpg"),
        ServerInfoDto("10", "Сервер 10", 600, "https://i.postimg.cc/QcY6h8tT/server10.jpg")
    )

    private val fakeServer = ServerInfoExpandedDto(
        id = "serverId",
        name = "Kotlin Developers",
        photoURL = "https://example.com/servers/kotlin/avatar.png",
        isOwner = true,
        members = listOf(
            ServerInfoExpandedDto.ServerMemberDto(
                id = "user_123",
                name = "Анна",
                username = "anna_dev",
                status = "online",
                photoURL = "https://example.com/users/anna/avatar.png",
                roles = listOf(
                    ServerInfoExpandedDto.ServerMemberDto.ServerRoleDto(
                        id = "role_admin",
                        name = "Admin",
                        color = "#FF0000"
                    ),
                    ServerInfoExpandedDto.ServerMemberDto.ServerRoleDto(
                        id = "role_mod",
                        name = "Moderator",
                        color = "#00FF00"
                    )
                )
            ),
            ServerInfoExpandedDto.ServerMemberDto(
                id = "user_456",
                name = "Петр",
                username = "petr_kotlin",
                status = "idle",
                photoURL = "https://example.com/users/petr/avatar.png",
                roles = listOf(
                    ServerInfoExpandedDto.ServerMemberDto.ServerRoleDto(
                        id = "role_dev",
                        name = "Developer",
                        color = "#0000FF"
                    )
                )
            ),
            ServerInfoExpandedDto.ServerMemberDto(
                id = "user_789",
                name = "Мария",
                username = "maria_codes",
                status = "dnd",
                photoURL = null,
                roles = emptyList()
            ),
            ServerInfoExpandedDto.ServerMemberDto(
                id = "user_765",
                name = "Мария1",
                username = "maria_codes1",
                status = "dnd",
                photoURL = null,
                roles = emptyList()
            ),
            ServerInfoExpandedDto.ServerMemberDto(
                id = "user_7345",
                name = "Мария2",
                username = "maria_codes2",
                status = "dnd",
                photoURL = null,
                roles = emptyList()
            )
        ),
        textChannels = listOf(
            ServerInfoExpandedDto.TextChannelDto(
                id = "channel_1",
                name = "общий"
            ),
            ServerInfoExpandedDto.TextChannelDto(
                id = "channel_2",
                name = "вопросы-ответы"
            ),
            ServerInfoExpandedDto.TextChannelDto(
                id = "channel_3",
                name = "код-ревью"
            )
        ),
        voiceChannels = listOf(
            ServerInfoExpandedDto.VoiceChannelDto(
                id = "voice_1",
                name = "General"
            ),
            ServerInfoExpandedDto.VoiceChannelDto(
                id = "voice_2",
                name = "Work Together"
            )
        )
    )

    private val fakeInvitations = listOf(
        InvitationDto(
            id = "inv_001",
            userId = "user_123",
            name = "Анна",
            username = "anna_dev",
            status = "pending",
            photoURL = "https://example.com/users/anna/avatar.png",
            expTime = LocalDateTime.now().plusDays(7)
        ),
        InvitationDto(
            id = "inv_002",
            userId = "user_789",
            name = "Мария",
            username = "maria_codes",
            status = "accepted",
            photoURL = null,
            expTime = LocalDateTime.now().plusDays(3)
        ),
        InvitationDto(
            id = "inv_003",
            userId = "user_101",
            name = "Дмитрий",
            username = "dmitry_android",
            status = "expired",
            photoURL = "https://example.com/users/dmitry/avatar.png",
            expTime = LocalDateTime.now().minusDays(2)
        ),
        InvitationDto(
            id = "inv_004",
            userId = "user_202",
            name = "Елена",
            username = "elena_dev",
            status = "pending",
            photoURL = "https://example.com/users/elena/avatar.png",
            expTime = LocalDateTime.now().plusDays(14)
        )
    )

    private val serverRoles = listOf(
        RoleInfoDto(
            id = "role_admin",
            name = "Admin",
            color = "#FF0000",
            members = listOf(
                RoleInfoDto.RoleMemberDto(
                    id = "user_123",
                    name = "Анна",
                    username = "anna_dev",
                    status = "online",
                    photoURL = "https://example.com/users/anna/avatar.png"
                )
            )
        ),
        RoleInfoDto(
            id = "role_mod",
            name = "Moderator",
            color = "#00FF00",
            members = listOf(
                RoleInfoDto.RoleMemberDto(
                    id = "user_123",
                    name = "Анна",
                    username = "anna_dev",
                    status = "online",
                    photoURL = "https://example.com/users/anna/avatar.png"
                ),
                RoleInfoDto.RoleMemberDto(
                    id = "user_456",
                    name = "Петр",
                    username = "petr_kotlin",
                    status = "idle",
                    photoURL = "https://example.com/users/petr/avatar.png"
                )
            )
        ),
        RoleInfoDto(
            id = "role_dev",
            name = "Developer",
            color = "#0000FF",
            members = listOf(
                RoleInfoDto.RoleMemberDto(
                    id = "user_456",
                    name = "Петр",
                    username = "petr_kotlin",
                    status = "idle",
                    photoURL = "https://example.com/users/petr/avatar.png"
                ),
                RoleInfoDto.RoleMemberDto(
                    id = "user_789",
                    name = "Мария",
                    username = "maria_codes",
                    status = "dnd",
                    photoURL = null
                )
            )
        ),
        RoleInfoDto(
            id = "role_muted",
            name = "Muted",
            color = "#808080",
            members = emptyList()
        )
    )

    private val fakeUserRoles = listOf(
        RoleInfoDto(
            id = "role_admin",
            name = "Admin",
            color = "#FF0000",
            members = listOf(
                RoleInfoDto.RoleMemberDto(
                    id = "user_123",
                    name = "Анна",
                    username = "anna_dev",
                    status = "online",
                    photoURL = "https://example.com/users/anna/avatar.png"
                )
            )
        ),
        RoleInfoDto(
            id = "role_mod",
            name = "Moderator",
            color = "#00FF00",
            members = listOf(
                RoleInfoDto.RoleMemberDto(
                    id = "user_123",
                    name = "Анна",
                    username = "anna_dev",
                    status = "online",
                    photoURL = "https://example.com/users/anna/avatar.png"
                )
            )
        )
    )

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

    override suspend fun getFriends(): Response<List<UserInfoDto>> {
        return Response.success(fakeFriends)
    }

    override suspend fun getServers(): Response<List<ServerInfoDto>> {
        return Response.success(fakeServers)
    }

    override suspend fun changeName(newName: String): Response<String> {
        return Response.success("true")
        //return Response.error(0, ResponseBody.EMPTY)
    }

    override suspend fun changeStatus(status: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun changeDesc(desc: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun createFriendRequest(username: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun respondToFriendshipRequest(
        friendshipId: String,
        status: String
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun deleteFriendship(userId: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun getUserInfoExtended(userId: String): Response<UserInfoExtendedDto> {
        return Response.success(fakeUserExtended)
    }

    override suspend fun getCommonServers(userId: String): Response<List<ServerInfoDto>> {
        return Response.success(fakeServers)
    }

    override suspend fun createServerRole(
        serverId: String,
        createRoleDto: CreateRoleDto
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun createServer(createServerDto: CreateServerDto): Response<String> {
        return Response.success("true")
    }

    override suspend fun createChannel(
        serverId: String,
        createChannelDto: CreateChannelDto
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun deleteChannel(
        serverId: String,
        channelId: String
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun deleteServerInvitation(
        serverId: String,
        invitationId: String
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun deleteServerMember(
        serverId: String,
        userId: String
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun deleteServer(serverId: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun getServerInfo(serverId: String): Response<ServerInfoExpandedDto> {
        return Response.success(fakeServer)
    }

    override suspend fun getServerInvitations(serverId: String): Response<List<InvitationDto>> {
        return Response.success(fakeInvitations)
    }

    override suspend fun getFriendsNotInServer(serverId: String): Response<List<UserInfoDto>> {
        return Response.success(fakeFriends)
    }

    override suspend fun getServerRoles(serverId: String): Response<List<RoleInfoDto>> {
        return Response.success(serverRoles)
    }

    override suspend fun getServerUserRoles(
        userId: String,
        serverId: String
    ): Response<List<RoleInfoDto>> {
        return Response.success(fakeUserRoles)
    }

    override suspend fun joinServer(): Response<String> {
        return Response.success("true")
    }

    override suspend fun patchChannel(): Response<String> {
        return Response.success("true")
    }

    override suspend fun patchServerOwner(
        userId: String,
        serverId: String
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun patchServer(): Response<String> {
        return Response.success("true")
    }

    override suspend fun patchRole(): Response<String> {
        return Response.success("true")
    }

    override suspend fun sendServerInvitation(
        userId: String,
        serverId: String
    ): Response<String> {
        return Response.success("true")
    }
}
