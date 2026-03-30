package ru.hse.app.androidApp.data.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import ru.hse.app.androidApp.data.model.ChannelInfoDto
import ru.hse.app.androidApp.data.model.CreateChannelDto
import ru.hse.app.androidApp.data.model.CreateRoleDto
import ru.hse.app.androidApp.data.model.CreateServerDto
import ru.hse.app.androidApp.data.model.RoleInfoDto
import ru.hse.app.androidApp.data.model.ServerInfoDto
import ru.hse.app.androidApp.data.model.ServerInfoExpandedDto
import ru.hse.app.androidApp.data.model.ServerInviteDto
import ru.hse.app.androidApp.data.model.TypeDto
import ru.hse.app.androidApp.data.model.UpdateChannelDto
import ru.hse.app.androidApp.data.model.UpdateProfileDto
import ru.hse.app.androidApp.data.model.UpdateRoleDto
import ru.hse.app.androidApp.data.model.UpdateServerDto
import ru.hse.app.androidApp.data.model.UserDto
import ru.hse.app.androidApp.data.model.UserInfoDto
import ru.hse.app.androidApp.data.model.UserInfoExtendedDto
import java.time.LocalDateTime
import java.util.UUID

class FakeApiService : ApiService {

    private var fakeUser =
        UserDto(
            "Юлия Кухтина",
            "yuulkht",
            "testuser@example.com",
            "ONLINE",
            "https://i.postimg.cc/J4DLnLCS/accountphoto.jpg",
            "Описание профиля описание профиля описание профиля описание профиля"
        )

    private val fakeUserExtended =
        UserInfoExtendedDto(
            "1",
            "Юлия Кухтина",
            "yuulkht",
            "ONLINE",
            "https://i.postimg.cc/J4DLnLCS/accountphoto.jpg",
            "Описание профиля описание профиля описание профиля описание профиля",
            UserInfoExtendedDto.StatusType.FRIEND
        )

    private val fakeFriends = mutableListOf(
        UserInfoDto(
            "1",
            "Алексей Иванов",
            "alex_ivanov",
            "ONLINE",
            "https://i.postimg.cc/1XfF8BZh/friend1.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "2",
            "Мария Петрова",
            "masha_petrov",
            "INVISIBLE",
            "https://i.postimg.cc/K8Jxt5wQ/friend2.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "3",
            "Ирина Смирнова",
            "irina_smirnov",
            "DO_NOT_DISTURB",
            "https://i.postimg.cc/3RmDC39Y/friend3.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "4",
            "Дмитрий Козлов",
            "dmitry_kozlov",
            "OFFLINE",
            "https://i.postimg.cc/tgG6rqDh/friend4.jpg",
            TypeDto.OUTGOING_REQUEST
        ),
        UserInfoDto(
            "5",
            "Елена Федорова",
            "elena_fedorova",
            "ONLINE",
            "https://i.postimg.cc/MTf5d7Z8/friend5.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "6",
            "Иван Ребров",
            "ivan_rebrov",
            "DO_NOT_DISTURB",
            "https://i.postimg.cc/fyzsbpdZ/friend6.jpg",
            TypeDto.INCOMING_REQUEST
        ),
        UserInfoDto(
            "7",
            "Татьяна Белова",
            "tatiana_belova",
            "INVISIBLE",
            "https://i.postimg.cc/9F0CjTjR/friend7.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "8",
            "Максим Сидоров",
            "maxim_sidorov",
            "ONLINE",
            "https://i.postimg.cc/7Z1vBGzF/friend8.jpg",
            TypeDto.OUTGOING_REQUEST
        ),
        UserInfoDto(
            "9",
            "Ольга Червонова",
            "olga_chervonova",
            "OFFLINE",
            "https://i.postimg.cc/Y2myr5x6/friend9.jpg",
            TypeDto.FRIEND
        ),
        UserInfoDto(
            "10",
            "Петр Лебедев",
            "peter_lebedev",
            "INVISIBLE",
            "https://i.postimg.cc/mkGYD5Kp/friend10.jpg",
            TypeDto.INCOMING_REQUEST
        )
    )

    private val fakeServers = mutableListOf(
        ServerInfoDto("1", "Сервер 1", "https://i.postimg.cc/2yzCRhY8/server1.jpg"),
        ServerInfoDto("2", "Сервер 2", "https://i.postimg.cc/XJH1pcGs/server2.jpg"),
        ServerInfoDto("3", "Сервер 3", null),
        ServerInfoDto("4", "Сервер 4", "https://i.postimg.cc/mg9f6RYn/server4.jpg"),
        ServerInfoDto("5", "Сервер 5", "https://i.postimg.cc/BZkQQH7X/server5.jpg"),
        ServerInfoDto("6", "Сервер 6", "https://i.postimg.cc/vZq3ZyGG/server6.jpg"),
        ServerInfoDto("7", "Сервер 7", null),
        ServerInfoDto("8", "Сервер 8", "https://i.postimg.cc/mtJ7VHTN/server8.jpg"),
        ServerInfoDto("9", "Сервер 9", "https://i.postimg.cc/8C3bbFGG/server9.jpg"),
        ServerInfoDto("10", "Сервер 10", "https://i.postimg.cc/QcY6h8tT/server10.jpg")
    )

    private var fakeServer = ServerInfoExpandedDto(
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
            ),
            ServerInfoExpandedDto.ServerMemberDto(
                id = "user_73451",
                name = "Мария3",
                username = "maria_codes3",
                status = "dnd",
                photoURL = null,
                roles = emptyList()
            ),
            ServerInfoExpandedDto.ServerMemberDto(
                id = "user_73452",
                name = "Мария4",
                username = "maria_codes4",
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

    private val fakeInvitations = mutableListOf(
        ServerInviteDto(
            code = "jweiqjweiq",
            serverId = "serverId",
            creatorId = "id",
            expiresAt = LocalDateTime.now().plusDays(7),
            serverName = "composers",
            creatorName = "vanya",
            inviteUrl = "https://hasslspace.ru/jweiqjweiq"
        ),
        ServerInviteDto(
            code = "sdfsdfw",
            serverId = "serverId",
            creatorId = "id",
            expiresAt = LocalDateTime.now().plusDays(3),
            serverName = "composers",
            creatorName = "vanya",
            inviteUrl = "https://hassl-space.ru/sdfsdfw"
        ),
        ServerInviteDto(
            code = "joifjcsc",
            serverId = "serverId",
            creatorId = "id",
            expiresAt = LocalDateTime.now().plusDays(2),
            serverName = "composers",
            creatorName = "vanya",
            inviteUrl = "https://hassl-space.ru/joifjcsc"
        ),
        ServerInviteDto(
            code = "cjprwjw[jcw",
            serverId = "serverId",
            creatorId = "id",
            expiresAt = LocalDateTime.now().plusDays(14),
            serverName = "composers",
            creatorName = "vanya",
            inviteUrl = "https://hassl-space.ru/cjprwjw"
        )
    )

    private val serverRoles = mutableListOf(
        RoleInfoDto(
            id = "role_admin",
            name = "Admin",
            color = "#FF0000",
            position = null,
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
            position = null,
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
            position = 1,
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
            position = null,
            members = emptyList()
        )
    )

    private val fakeUserRoles = listOf(
        RoleInfoDto(
            id = "role_admin",
            name = "Admin",
            color = "#FF0000",
            position = null,
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
            position = null,
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

    override suspend fun uploadPhoto(
        photo: MultipartBody.Part,
        type: RequestBody,
        photoUrl: RequestBody?
    ): Response<String> {
        return Response.success("Данные успешно сохранены")
    }

    override suspend fun updateUserProfile(updateProfileDto: UpdateProfileDto): Response<String> {
        fakeUser = fakeUser.copy(
            name = updateProfileDto.name ?: fakeUser.name,
            photoURL = updateProfileDto.photoUrl ?: fakeUser.photoURL,
            description = updateProfileDto.description ?: fakeUser.description,
        )
        return Response.success("true")
    }

    override suspend fun getFriends(): Response<List<UserInfoDto>> {
        return Response.success(fakeFriends)
    }

    override suspend fun getServers(): Response<List<ServerInfoDto>> {
        return Response.success(fakeServers)
    }

    override suspend fun changeStatus(status: String): Response<String> {
        fakeUser = fakeUser.copy(
            status = status
        )
        return Response.success("true")
    }

    override suspend fun createFriendRequest(username: String): Response<String> {
        fakeFriends.addLast(
            UserInfoDto(
                "11",
                "Александр Миронов",
                "mironov",
                "ONLINE",
                "https://i.postimg.cc/7Z1vBGzF/friend8.jpg",
                TypeDto.OUTGOING_REQUEST
            ),
        )
        return Response.success("true")
    }

    override suspend fun respondToFriendshipRequest(
        userId: String,
        status: String
    ): Response<String> {
        if (status == "ACCEPTED") {
            val candidate = fakeFriends.find { it.id == userId }?.copy(type = TypeDto.FRIEND)

            fakeFriends.remove(
                fakeFriends.find { it.id == userId }
            )
            candidate?.let {
                fakeFriends.addLast(candidate)
            }

        } else {
            fakeFriends.remove(
                fakeFriends.find { it.id == userId }
            )
        }
        return Response.success("true")
    }

    override suspend fun deleteFriendship(userId: String): Response<String> {
        fakeFriends.remove(
            fakeFriends.find { it.id == userId }
        )
        return Response.success("true")
    }

    override suspend fun getUserInfoExtended(userId: String): Response<UserInfoExtendedDto> {
        val friend = fakeFriends.find { it.id == userId }

        return if (friend != null) {
            Response.success(
                UserInfoExtendedDto(
                    id = friend.id,
                    name = friend.name,
                    nickname = friend.nickname,
                    status = friend.status,
                    photoURL = friend.photoURL,
                    description = getDescriptionForUser(friend.id),
                    friendshipStatus = when (friend.type) {
                        TypeDto.FRIEND -> UserInfoExtendedDto.StatusType.FRIEND
                        TypeDto.OUTGOING_REQUEST -> UserInfoExtendedDto.StatusType.OUTGOING_REQUEST
                        TypeDto.INCOMING_REQUEST -> UserInfoExtendedDto.StatusType.INCOMING_REQUEST
                    },
                )
            )
        } else {
            Response.error(404, ResponseBody.create(null, "User not found"))
        }
    }

    private fun getDescriptionForUser(userId: String): String {
        return when (userId) {
            "1" -> "Люблю программировать и играть в шахматы"
            "2" -> "Фотограф и путешественник"
            "3" -> "Музыкант, играю на гитаре"
            "4" -> "Студент, учусь на программиста"
            "5" -> "Дизайнер интерфейсов"
            "6" -> "Спортсмен, увлекаюсь бегом"
            "7" -> "Книжный червь, читаю фантастику"
            "8" -> "Люблю готовить и экспериментировать"
            "9" -> "Путешествую по миру"
            "10" -> "Изучаю иностранные языки"
            else -> "Описание профиля"
        }
    }

    override suspend fun getCommonServers(userId: String): Response<List<ServerInfoDto>> {
        return Response.success(fakeServers)
    }

    override suspend fun createServerRole(
        serverId: String,
        createRoleDto: CreateRoleDto
    ): Response<String> {
        serverRoles.addLast(
            RoleInfoDto(
                id = UUID.randomUUID().toString(),
                name = createRoleDto.name,
                color = createRoleDto.color,
                position = createRoleDto.position,
                members = listOf()
            )
        )
        return Response.success("true")
    }

    override suspend fun createServer(createServerDto: CreateServerDto): Response<String> {
        fakeServers.addLast(
            ServerInfoDto(
                id = "11",
                name = createServerDto.name,
                photoURL = createServerDto.photoUrl
            )
        )
        return Response.success("true")
    }

    override suspend fun createChannel(
        serverId: String,
        createChannelDto: CreateChannelDto
    ): Response<String> {
        if (createChannelDto.type == "TEXT") {
            fakeServer = fakeServer.copy(
                textChannels = fakeServer.copy().textChannels.plus(
                    ServerInfoExpandedDto.TextChannelDto(
                        id = UUID.randomUUID().toString(),
                        name = createChannelDto.name
                    )
                )
            )
        } else {
            fakeServer = fakeServer.copy(
                voiceChannels = fakeServer.copy().voiceChannels.plus(
                    ServerInfoExpandedDto.VoiceChannelDto(
                        id = UUID.randomUUID().toString(),
                        name = createChannelDto.name
                    )
                )
            )
        }

        return Response.success("true")
    }

    override suspend fun deleteChannel(
        serverId: String,
        channelId: String
    ): Response<String> {
        fakeServer = fakeServer.copy(
            voiceChannels = fakeServer.copy().voiceChannels.minus(
                fakeServer.copy().voiceChannels.filter { it.id == channelId }.toSet()
            ),
            textChannels = fakeServer.copy().textChannels.minus(
                fakeServer.copy().textChannels.filter { it.id == channelId }.toSet()
            )
        )
        return Response.success("true")
    }

    override suspend fun deleteServerInvitation(
        serverId: String,
        invitationId: String
    ): Response<String> {
        fakeInvitations.remove(
            fakeInvitations.find { it.code == invitationId }
        )
        return Response.success("true")
    }

    override suspend fun deleteServerMember(
        serverId: String,
        targetUserId: String
    ): Response<String> {
        val updatedMembers = fakeServer.members.filter { it.id != targetUserId }
        fakeServer = fakeServer.copy(
            members = updatedMembers
        )
        return Response.success("true")
    }

    override suspend fun patchChannel(
        serverId: String,
        channelId: String,
        updateChannelDto: UpdateChannelDto
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun updateServerOwner(
        userId: String,
        serverId: String
    ): Response<String> {
        fakeServer = fakeServer.copy(
            isOwner = false,
        )
        return Response.success("true")
    }

    override suspend fun patchServer(
        serverId: String,
        updateServerDto: UpdateServerDto
    ): Response<String> {
        fakeServer = fakeServer.copy(
            name = updateServerDto.name ?: fakeServer.copy().name,
            photoURL = updateServerDto.photoUrl ?: fakeServer.copy().photoURL,
        )
        return Response.success("true")
    }

    override suspend fun patchRole(
        serverId: String,
        roleId: String,
        updateRoleDto: UpdateRoleDto
    ): Response<String> {
        if (serverId != fakeServer.id) {
            return Response.error(404, "Server not found".toResponseBody(null))
        }
        val roleIndex = serverRoles.indexOfFirst { it.id == roleId }
        if (roleIndex == -1) {
            return Response.error(404, "Role not found".toResponseBody(null))
        }
        val existingRole = serverRoles[roleIndex]

        val updatedRole = existingRole.copy(
            name = updateRoleDto.name ?: existingRole.name,
            color = updateRoleDto.color ?: existingRole.color,
            position = updateRoleDto.position ?: existingRole.position
        )

        serverRoles[roleIndex] = updatedRole

        val updatedMembers = fakeServer.members.map { member ->
            val updatedMemberRoles = member.roles?.map { role ->
                if (role.id == roleId) {

                    role.copy(
                        name = updateRoleDto.name ?: role.name,
                        color = updateRoleDto.color ?: role.color
                    )
                } else {
                    role
                }
            }
            member.copy(roles = updatedMemberRoles)
        }

        fakeServer = fakeServer.copy(members = updatedMembers)

        return Response.success("Role $roleId successfully updated")
    }

    override suspend fun deleteServer(serverId: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun deleteRole(
        serverId: String,
        roleId: String
    ): Response<String> {
        serverRoles.remove(
            serverRoles.find { it.id == roleId }
        )
        return Response.success("true")
    }

    override suspend fun assignRole(
        serverId: String,
        targetUserId: String,
        roleId: String
    ): Response<String> {
        return Response.success("true")
//        return Response.error(1, "true".toResponseBody(null))
    }

    override suspend fun revokeRole(
        serverId: String,
        targetUserId: String,
        roleId: String
    ): Response<String> {
        return Response.success("true")
    }

    override suspend fun getServerInfo(serverId: String): Response<ServerInfoExpandedDto> {
        return Response.success(fakeServer)
    }

    override suspend fun createInvitation(serverId: String): Response<ServerInviteDto> {
        return Response.success(fakeInvitations.get(0))
    }

    override suspend fun getServerInvitations(serverId: String): Response<List<ServerInviteDto>> {
        return Response.success(fakeInvitations)
    }

    override suspend fun getFriendsNotInServer(serverId: String): Response<List<UserInfoDto>> {
        return Response.success(fakeFriends)
    }

    override suspend fun getServerRoles(serverId: String): Response<List<RoleInfoDto>> {
        return Response.success(serverRoles)
    }

    override suspend fun joinServer(code: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun leaveServer(serverId: String): Response<String> {
        return Response.success("true")
    }

    override suspend fun getChannelInfo(
        serverId: String,
        channelId: String
    ): Response<ChannelInfoDto> {

        val channel = when (channelId) {
            "channel_1" -> ServerInfoExpandedDto.TextChannelDto("channel_1", "общий")
            "channel_2" -> ServerInfoExpandedDto.TextChannelDto("channel_2", "вопросы-ответы")
            "channel_3" -> ServerInfoExpandedDto.TextChannelDto("channel_3", "код-ревью")
            "voice_1" -> ServerInfoExpandedDto.VoiceChannelDto("voice_1", "General")
            "voice_2" -> ServerInfoExpandedDto.VoiceChannelDto("voice_2", "Work Together")
            else -> return Response.success(
                ChannelInfoDto(
                    name = "Unknown Channel",
                    isPrivate = false,
                    type = "TEXT",
                    limit = null,
                    members = emptyList(),
                    roles = emptyList(),
                    id = "1"
                )
            )
        }

        val channelName = if (channel is ServerInfoExpandedDto.TextChannelDto) {
            channel.name
        } else if (channel is ServerInfoExpandedDto.VoiceChannelDto) {
            channel.name
        } else {
            "Unknown"
        }


        val isPrivate = when (channelId) {
            "channel_2", "voice_2" -> true
            else -> false
        }

        val channelType = when (channel) {
            is ServerInfoExpandedDto.TextChannelDto -> "TEXT"
            is ServerInfoExpandedDto.VoiceChannelDto -> "VOICE"
            else -> "TEXT"
        }

        val limit = when (channelType) {
            "VOICE" -> 10
            else -> null
        }


        val members = fakeServer.members.map { it.id }


        val roles = serverRoles.map { it.id }


        val (finalMembers, finalRoles) = if (isPrivate) {
            when (channelId) {
                "channel_2" -> Pair(
                    listOf("user_123", "user_456"),
                    listOf("role_admin", "role_mod")
                )

                "voice_2" -> Pair(
                    listOf("user_123", "user_456", "user_789"),
                    listOf("role_admin", "role_mod", "role_dev")
                )

                else -> Pair(members.take(3), roles.take(2))
            }
        } else {
            Pair(members, roles)
        }

        return Response.success(
            ChannelInfoDto(
                name = channelName,
                isPrivate = isPrivate,
                type = channelType,
                limit = limit,
                members = finalMembers,
                roles = finalRoles,
                id = channelId
            )
        )
    }
}
