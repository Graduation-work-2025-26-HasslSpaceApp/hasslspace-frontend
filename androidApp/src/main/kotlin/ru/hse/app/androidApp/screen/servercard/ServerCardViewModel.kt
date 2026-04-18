package ru.hse.app.androidApp.screen.servercard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Bitmap
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.service.common.ColorService
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.usecase.channel.AssignChannelPermissionUseCase
import ru.hse.app.androidApp.domain.usecase.channel.CreateChannelUseCase
import ru.hse.app.androidApp.domain.usecase.channel.DeleteChannelPermissionUseCase
import ru.hse.app.androidApp.domain.usecase.channel.DeleteChannelUseCase
import ru.hse.app.androidApp.domain.usecase.channel.GetChannelInfoUseCase
import ru.hse.app.androidApp.domain.usecase.channel.GetChannelPermissionsUseCase
import ru.hse.app.androidApp.domain.usecase.channel.PatchChannelPropertiesUseCase
import ru.hse.app.androidApp.domain.usecase.chats.MarkChatAsReadUseCase
import ru.hse.app.androidApp.domain.usecase.chats.StartChatUseCase
import ru.hse.app.androidApp.domain.usecase.friends.CreateFriendRequestUseCase
import ru.hse.app.androidApp.domain.usecase.friends.DeleteFriendshipUseCase
import ru.hse.app.androidApp.domain.usecase.friends.GetChosenUserCommonServersUseCase
import ru.hse.app.androidApp.domain.usecase.friends.GetUserInfoExtendedUseCase
import ru.hse.app.androidApp.domain.usecase.friends.RespondToFriendshipRequestUseCase
import ru.hse.app.androidApp.domain.usecase.invitations.SendServerInvitationUseCase
import ru.hse.app.androidApp.domain.usecase.profile.LoadUserInfoUseCase
import ru.hse.app.androidApp.domain.usecase.roles.GetServerRolesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetFriendsNotInServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInfoUseCase
import ru.hse.app.androidApp.domain.usecase.servers.LeaveServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.SearchMembersUseCase
import ru.hse.app.androidApp.domain.usecase.voice.GetVoiceRoomTokenUseCase
import ru.hse.app.androidApp.domain.usecase.voice.SendVoiceInviteToUserUseCase
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.TextChannelUiModel
import ru.hse.app.androidApp.ui.entity.model.VoiceChannelUiModel
import ru.hse.app.androidApp.ui.entity.model.call.events.GetTokenEvent
import ru.hse.app.androidApp.ui.entity.model.chats.events.StartChatEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.CreateFriendRequestEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.DeleteFriendshipEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadChosenUserCommonServersEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadChosenUserEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadUserDataEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.RespondToFriendRequestEvent
import ru.hse.app.androidApp.ui.entity.model.servers.ServerCardUiModel
import ru.hse.app.androidApp.ui.entity.model.servers.ServerCardUiState
import ru.hse.app.androidApp.ui.entity.model.servers.ServerExpandedUiModel
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetFriendsNotInServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.LeaveServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.LoadChosenChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.PatchChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.SendServerInvitationEvent
import ru.hse.app.androidApp.ui.entity.model.servers.toUi
import ru.hse.app.androidApp.ui.entity.model.toInvitationUi
import ru.hse.app.androidApp.ui.entity.model.toStatusPresentation
import ru.hse.app.androidApp.ui.entity.model.toUi
import ru.hse.app.androidApp.ui.errorhandling.ErrorHandler
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ServerCardViewModel @Inject constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase,

    private val createChannelUseCase: CreateChannelUseCase,
    private val getChannelPermissionsUseCase: GetChannelPermissionsUseCase,
    private val assignChannelPermissionUseCase: AssignChannelPermissionUseCase,
    private val deleteChannelPermissionUseCase: DeleteChannelPermissionUseCase,

    private val deleteChannelUseCase: DeleteChannelUseCase,
    private val deleteServerUseCase: DeleteServerUseCase,
    private val leaveServerUseCase: LeaveServerUseCase,

    private val getServerInfoUseCase: GetServerInfoUseCase,
    private val getServerRolesUseCase: GetServerRolesUseCase,
    private val getFriendsNotInServerUseCase: GetFriendsNotInServerUseCase,
    private val getUserInfoExtendedUseCase: GetUserInfoExtendedUseCase,
    private val getChosenUserCommonServersUseCase: GetChosenUserCommonServersUseCase,
    private val getChannelInfoUseCase: GetChannelInfoUseCase,

    private val createFriendRequestUseCase: CreateFriendRequestUseCase,
    private val deleteFriendshipUseCase: DeleteFriendshipUseCase,
    private val respondToFriendshipRequestUseCase: RespondToFriendshipRequestUseCase,

    private val patchChannelPropertiesUseCase: PatchChannelPropertiesUseCase,

    private val sendServerInvitationUseCase: SendServerInvitationUseCase,
    private val searchMembersUseCase: SearchMembersUseCase,

    private val markChatAsReadUseCase: MarkChatAsReadUseCase,

    private val sendVoiceInviteToUserUseCase: SendVoiceInviteToUserUseCase,


    private val errorHandler: ErrorHandler,

    private val getVoiceRoomTokenUseCase: GetVoiceRoomTokenUseCase,

    private val startChatUseCase: StartChatUseCase,

    private val dataManager: DataManager,
    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val colorService: ColorService,

    val imageLoader: ImageLoader

) : ViewModel() {

    val isDarkTheme = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ServerCardUiState>(ServerCardUiState.Loading)
    val uiState: StateFlow<ServerCardUiState> = _uiState

    private val _createChannelEvent = MutableStateFlow<CreateChannelEvent?>(null)
    val createChannelEvent: StateFlow<CreateChannelEvent?> = _createChannelEvent

    // Delete events
    private val _deleteChannelEvent = MutableStateFlow<DeleteChannelEvent?>(null)
    val deleteChannelEvent: StateFlow<DeleteChannelEvent?> = _deleteChannelEvent

    private val _deleteServerEvent = MutableStateFlow<DeleteServerEvent?>(null)
    val deleteServerEvent: StateFlow<DeleteServerEvent?> = _deleteServerEvent

    // Get events
    private val _getServerInfoEvent = MutableStateFlow<GetServerInfoEvent?>(null)
    val getServerInfoEvent: StateFlow<GetServerInfoEvent?> = _getServerInfoEvent

    private val _getFriendsNotInServerEvent = MutableStateFlow<GetFriendsNotInServerEvent?>(null)
    val getFriendsNotInServerEvent: StateFlow<GetFriendsNotInServerEvent?> =
        _getFriendsNotInServerEvent

    private val _loadChosenUserEvent = MutableStateFlow<LoadChosenUserEvent?>(null)
    val loadChosenUserEvent: StateFlow<LoadChosenUserEvent?> = _loadChosenUserEvent

    private val _loadChosenUserCommonServersEvent =
        MutableStateFlow<LoadChosenUserCommonServersEvent?>(null)
    val loadChosenUserCommonServersEvent: StateFlow<LoadChosenUserCommonServersEvent?> =
        _loadChosenUserCommonServersEvent

    private val _loadChosenChannelEvent = MutableStateFlow<LoadChosenChannelEvent?>(null)
    val loadChosenChannelEvent: StateFlow<LoadChosenChannelEvent?> = _loadChosenChannelEvent

    private val _sendServerInvitationEvent = MutableStateFlow<SendServerInvitationEvent?>(null)
    val sendServerInvitationEvent: StateFlow<SendServerInvitationEvent?> =
        _sendServerInvitationEvent

    private val _patchChannelEvent = MutableStateFlow<PatchChannelEvent?>(null)
    val patchChannelEvent: StateFlow<PatchChannelEvent?> = _patchChannelEvent

    private val _getTokenEvent = MutableStateFlow<GetTokenEvent?>(null)
    val getTokenEvent: StateFlow<GetTokenEvent?> = _getTokenEvent

    private val _leaveServerEvent = MutableStateFlow<LeaveServerEvent?>(null)
    val leaveServerEvent: StateFlow<LeaveServerEvent?> = _leaveServerEvent

    private val _getVoiceChannelTokenEvent = MutableStateFlow<GetTokenEvent?>(null)
    val getVoiceChannelTokenEvent: StateFlow<GetTokenEvent?> = _getVoiceChannelTokenEvent

    private val _createFriendRequestEvent = MutableStateFlow<CreateFriendRequestEvent?>(null)
    val createFriendRequestEvent: StateFlow<CreateFriendRequestEvent?> = _createFriendRequestEvent

    private val _deleteFriendshipEvent = MutableStateFlow<DeleteFriendshipEvent?>(null)
    val deleteFriendshipEvent: StateFlow<DeleteFriendshipEvent?> = _deleteFriendshipEvent

    private val _loadUserInfoEvent = MutableStateFlow<LoadUserDataEvent?>(null)
    val loadUserInfoEvent: StateFlow<LoadUserDataEvent?> = _loadUserInfoEvent

    private val _startChatEvent = MutableStateFlow<StartChatEvent?>(null)
    val startChatEvent: StateFlow<StartChatEvent?> = _startChatEvent

    private val _respondToFriendshipRequestEvent =
        MutableStateFlow<RespondToFriendRequestEvent?>(null)
    val respondToFriendshipRequestEvent: StateFlow<RespondToFriendRequestEvent?> =
        _respondToFriendshipRequestEvent

    // Members
    val searchMembersText = mutableStateOf("")
    val searchedMembers = mutableStateListOf<ServerMemberUiModel>()
    val showFriendCard = mutableStateOf(false)
    val showCommonServers = mutableStateOf(false)

    // AddPeopleToServer
    val showAddFriendsSheet = mutableStateOf(false)

    // Text Channels
    val showTextChannelsSettings = mutableStateOf(false)
    val showTextChannelOptions = mutableStateOf(false)
    val chosenTextChannel: MutableState<TextChannelUiModel?> = mutableStateOf(null)

    // Voice Channels
    val showVoiceChannelsSettings = mutableStateOf(false)
    val showVoiceChannelOptions = mutableStateOf(false)
    val chosenVoiceChannel: MutableState<VoiceChannelUiModel?> = mutableStateOf(null)

    // Create Channel
    val creatingChannel = mutableStateOf(false)
    val showChooseMembersAndRoles = mutableStateOf(false)
    val typeOfCreatingChannel = mutableStateOf("TEXT")
    val newChannelName = mutableStateOf("")
    val newChannelIsPrivate = mutableStateOf(false)
    val limitNewChannel = mutableFloatStateOf(0f)

    // EditChannel
    val showEditChannel = mutableStateOf(false)
    val showChooseMembersAndRolesEditChannel = mutableStateOf(false)

    val showDeleteChannel = mutableStateOf(false)

    // Server Settings Bar
    val showServerSettingsSheet = mutableStateOf(false)
    val showDeleteServerDialog = mutableStateOf(false)
    val showLeaveServerDialog = mutableStateOf(false)


    // Channel Settings
    val loadedMembers = mutableStateListOf<FriendCheckboxUiModel>()
    val loadedRoles = mutableStateListOf<RoleMiniCheckboxUiModel>()

    fun onNewChannelNameChanged(value: String) {
        newChannelName.value = value
    }

    fun onNewChannelIsPrivate(isPrivate: Boolean) {
        newChannelIsPrivate.value = isPrivate
    }

    fun onLimitValueChange(value: Float) {
        limitNewChannel.floatValue = value
    }

    fun onEditChannelNameChanged(value: String) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            val updatedData = currentState.data.copy(
                editChannel = currentState.data.editChannel.copy(
                    name = value
                )
            )
            _uiState.value = currentState.copy(data = updatedData)
        }
    }

    fun onEditChannelIsPrivate(isPrivate: Boolean) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            val updatedData = currentState.data.copy(
                editChannel = currentState.data.editChannel.copy(
                    isPrivate = isPrivate
                )
            )
            _uiState.value = currentState.copy(data = updatedData)
        }
    }

    fun onEditChannelLimitValueChange(value: Float) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            val updatedData = currentState.data.copy(
                editChannel = currentState.data.editChannel.copy(
                    limit = value
                )
            )
            _uiState.value = currentState.copy(data = updatedData)
        }
    }

    fun onSearchMembersText(value: String) {
        searchMembersText.value = value
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            viewModelScope.launch {
                val result =
                    searchMembersUseCase(currentState.data.chosenServer.members.map { it }, value)
                searchedMembers.clear()
                searchedMembers.addAll(result)
            }
        }
    }

    fun createChannel(
        serverId: String,
        channelName: String,
        isPrivate: Boolean,
        type: String,
        limit: Float,
        roles: List<RoleMiniCheckboxUiModel> = listOf()
    ) {
        if (channelName.isEmpty()) {
            handleError("Название канала не может быть пустым")
            return
        }
        val roundLimit = limit.roundToInt()
        val rolesId = roles.filter { it.isChosen }.map { it.id }
        viewModelScope.launch {
            val data = CreateChannel(
                name = channelName,
                isPrivate = isPrivate,
                type = type,
                limit = if (roundLimit > 0f) roundLimit else null,
            )
            val result = createChannelUseCase(serverId, data)

            _createChannelEvent.value = result.fold(
                onSuccess = {
                    launch {
                        // todo загружать канал и добавлять туда роли
                    }
                    CreateChannelEvent.SuccessCreate
                },
                onFailure = {
                    CreateChannelEvent.Error("Ошибка при создании канала. " + it.message)
                }
            )
        }
    }

    suspend fun assignRoleToChannel(serverId: String, channelId: String, roleId: String) {
        val result = assignChannelPermissionUseCase(serverId, channelId, roleId)

        result.fold(
            onSuccess = {},
            onFailure = {
                errorHandler.handleError("Ошибка при назначении прав роли. " + it.message)
            }
        )
    }

    suspend fun deleteRoleFromChannel(serverId: String, channelId: String, roleId: String) {
        val result = deleteChannelPermissionUseCase(serverId, channelId, roleId)

        result.fold(
            onSuccess = {},
            onFailure = {
                errorHandler.handleError("Ошибка при удалении прав роли. " + it.message)
            }
        )
    }

    fun leaveServer(serverId: String) {
        viewModelScope.launch {
            val result = leaveServerUseCase(serverId)

            _leaveServerEvent.value = result.fold(
                onSuccess = {
                    LeaveServerEvent.Success
                },
                onFailure = { error ->
                    LeaveServerEvent.Error("Ошибка при удалении сервера. ${error.message}")
                }
            )
        }
    }

    fun patchChannel(
        serverId: String,
        channelId: String,
        channelName: String,
        isPrivate: Boolean,
        type: String,
        limit: Float,
        members: List<FriendCheckboxUiModel> = listOf(),
        roles: List<RoleMiniCheckboxUiModel> = listOf()
    ) {
        if (channelName.isEmpty()) {
            handleError("Название канала не может быть пустым")
            return
        }
        val roundLimit = limit.roundToInt()
        val membersId = members.filter { it.isChosen }.map { it.id }
        val rolesId = roles.filter { it.isChosen }.map { it.id }
        viewModelScope.launch {
            val result = patchChannelPropertiesUseCase(
                serverId = serverId,
                channelId = channelId,
                name = channelName,
                isPrivate = isPrivate,
                position = null,
                maxMembers = roundLimit
            )

            _patchChannelEvent.value = result.fold(
                onSuccess = {
                    PatchChannelEvent.Success
                },
                onFailure = {
                    PatchChannelEvent.Error("Ошибка при изменении канала. " + it.message)
                }
            )
        }
    }

    fun deleteChannel(
        serverId: String,
        channelId: String
    ) {
        viewModelScope.launch {
            val result = deleteChannelUseCase(serverId, channelId)

            _deleteChannelEvent.value = result.fold(
                onSuccess = {
                    DeleteChannelEvent.SuccessDelete
                },
                onFailure = { error ->
                    DeleteChannelEvent.Error("Ошибка при удалении канала. ${error.message}")
                }
            )
        }
    }

    fun onJoinVoiceChannelClick(
        serverId: String,
        memberName: String,
        channelId: String,
        channelName: String,
    ) {
        viewModelScope.launch {
            val channelInfoResult = getChannelInfoUseCase(serverId, channelId)

            _getVoiceChannelTokenEvent.value = channelInfoResult.fold(
                onSuccess = { channel ->
                    val result = getVoiceRoomTokenUseCase(
                        name = memberName,
                        roomName = channelId,
                        roomType = "SERVER"
                    )
                    result.fold(
                        onSuccess = { token ->
                            GetTokenEvent.Success(
                                token,
                                channelName,
                                videoEnabled = false,
                                channel.limit ?: 0
                            )
                        },
                        onFailure = {
                            GetTokenEvent.Error("Ошибка при подключении к голосовому каналу. " + it.message)
                        }
                    )
                },
                onFailure = { error ->
                    GetTokenEvent.Error("Ошибка при получении информации о канале. " + error.message)
                }
            )
        }

    }

    fun onCallClick(targetUserId: String, memberName: String, roomName: String) {
        viewModelScope.launch {
            val resultChatId = startChatUseCase(targetUserId)

            _getTokenEvent.value = resultChatId.fold(
                onSuccess = { chatId ->
                    val result = getVoiceRoomTokenUseCase(
                        name = memberName,
                        roomName = chatId,
                        roomType = "PRIVATE_ROOM"
                    )

                    result.fold(
                        onSuccess = { token ->
                            viewModelScope.launch {
                                sendVoiceInviteToUserUseCase(targetUserId, memberName)
                            }
                            GetTokenEvent.Success(token, roomName, videoEnabled = false)
                        },
                        onFailure = {
                            GetTokenEvent.Error("Ошибка при подключении к звонку. " + it.message)
                        }
                    )
                },
                onFailure = {
                    GetTokenEvent.Error("Ошибка при получении комнаты. " + it.message)
                }
            )
        }
    }

    fun onMessageClick(userId: String) {
        viewModelScope.launch {
            val result = startChatUseCase(userId)

            _startChatEvent.value = result.fold(
                onSuccess = { chatId ->
                    StartChatEvent.Success(chatId)
                },
                onFailure = {
                    StartChatEvent.Error("Ошибка при создании чата. " + it.message)
                }
            )
        }
    }

    fun onVideoCallClick(targetUserId: String, memberName: String, roomName: String) {
        viewModelScope.launch {
            val resultChatId = startChatUseCase(targetUserId)

            _getTokenEvent.value = resultChatId.fold(
                onSuccess = { chatId ->
                    val result = getVoiceRoomTokenUseCase(
                        name = memberName,
                        roomName = chatId,
                        roomType = "PRIVATE_ROOM"
                    )

                    result.fold(
                        onSuccess = { token ->
                            viewModelScope.launch {
                                sendVoiceInviteToUserUseCase(targetUserId, memberName)
                            }
                            GetTokenEvent.Success(token, roomName, videoEnabled = true)
                        },
                        onFailure = {
                            GetTokenEvent.Error("Ошибка при подключении к видеозвонку. " + it.message)
                        }
                    )
                },
                onFailure = {
                    GetTokenEvent.Error("Ошибка при получении комнаты. " + it.message)
                }
            )
        }
    }

    fun respondFriend(userId: String, status: String) {
        viewModelScope.launch {
            val result =
                respondToFriendshipRequestUseCase(userId, status)

            _respondToFriendshipRequestEvent.value = result.fold(
                onSuccess = {
                    RespondToFriendRequestEvent.SuccessRespond
                },
                onFailure = {
                    RespondToFriendRequestEvent.Error("Ошибка при изменении данных. " + it.message)
                }
            )
        }
    }

    fun deleteFriendship(userId: String) {
        viewModelScope.launch {
            val result = deleteFriendshipUseCase(userId)

            _deleteFriendshipEvent.value = result.fold(
                onSuccess = {
                    DeleteFriendshipEvent.SuccessDelete
                },
                onFailure = {
                    DeleteFriendshipEvent.Error("Ошибка при удалении из друзей. " + it.message)
                }
            )
        }
    }

    fun createFriendshipRequest(nickname: String) {
        viewModelScope.launch {
            val result = createFriendRequestUseCase(nickname)

            _createFriendRequestEvent.value = result.fold(
                onSuccess = {
                    CreateFriendRequestEvent.SuccessRequest(nickname)
                },
                onFailure = {
                    CreateFriendRequestEvent.Error("Ошибка при создании заявки" + it.message)
                }
            )
        }
    }

    fun deleteServer(serverId: String) {
        viewModelScope.launch {
            val result = deleteServerUseCase(serverId)

            _deleteServerEvent.value = result.fold(
                onSuccess = {
                    DeleteServerEvent.SuccessDelete
                },
                onFailure = { error ->
                    DeleteServerEvent.Error("Ошибка при удалении сервера. ${error.message}")
                }
            )
        }
    }

    fun getServerInfo(serverId: String) {
        viewModelScope.launch {
            val result = getServerInfoUseCase(serverId)

            _getServerInfoEvent.value = result.fold(
                onSuccess = { serverInfo ->
                    val serverUi = serverInfo.toUi()
                    _uiState.value = ServerCardUiState.Success(
                        data = ServerCardUiModel(
                            chosenServer = serverInfo.toUi(),
                            friendsNotInServer = listOf(),
                            newChannelMembers = listOf(),
                            newChannelRoles = listOf(),
                            chosenUser = null,
                            currentUser = null,
                            chosenUserCommonServers = listOf(),
                            editChannel = ServerCardUiModel.EditChannelUiModel(
                                id = "",
                                name = "",
                                members = listOf(),
                                roles = listOf(),
                                isPrivate = false
                            )
                        )
                    )
                    searchedMembers.clear()
                    searchedMembers.addAll(serverUi.members)

                    loadUserData()
                    GetServerInfoEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetServerInfoEvent.Error("Ошибка при получении информации о сервере. ${error.message}")
                }
            )
        }
    }

    fun getFriendsNotInServer(serverId: String) {
        viewModelScope.launch {
            val result = getFriendsNotInServerUseCase(serverId)

            _getFriendsNotInServerEvent.value = result.fold(
                onSuccess = { users ->
                    val currentState = _uiState.value
                    if (currentState is ServerCardUiState.Success) {
                        val updatedData = currentState.data.copy(
                            friendsNotInServer = users.map { it.toInvitationUi() }
                        )
                        _uiState.value = ServerCardUiState.Success(updatedData)
                    }
                    GetFriendsNotInServerEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetFriendsNotInServerEvent.Error("Ошибка при получении информации о друзьях. ${error.message}")
                }
            )
        }
    }

    fun loadChosenChannel(server: ServerExpandedUiModel, channelId: String) {
        viewModelScope.launch {
            val result = getChannelInfoUseCase(server.id, channelId)
            val currentState = _uiState.value

            _loadChosenChannelEvent.value = result.fold(
                onSuccess = { channel ->
                    val checkboxRoles = if (channel.isPrivate) {
                        getChannelPermissionsUseCase(server.id, channel.id)
                            .fold({ roles ->
                                idsToRolesCheckboxInChannel(
                                    server,
                                    roles.map { it.id })
                            }, { idsToRolesCheckboxInChannel(server, emptyList()) })
                    } else {
                        idsToRolesCheckboxInChannel(server, null)
                    }

                    if (currentState is ServerCardUiState.Success) {
                        val updatedData = currentState.data.copy(
                            editChannel = currentState.data.editChannel.copy(
                                id = channel.id,
                                type = channel.type,
                                name = channel.name,
                                isPrivate = channel.isPrivate,
                                limit = channel.limit?.toFloat() ?: 0f,
                                members = emptyList(),
                                roles = checkboxRoles
                            )
                        )
                        _uiState.value = ServerCardUiState.Success(updatedData)
                    }
                    LoadChosenChannelEvent.SuccessLoad(channel.type)
                },
                onFailure = { error ->
                    LoadChosenChannelEvent.Error("Ошибка при получении информации о канале. ${error.message}")
                }
            )
        }
    }

    suspend fun idsToRolesCheckboxInChannel(
        server: ServerExpandedUiModel,
        ids: List<String>?
    ): List<RoleMiniCheckboxUiModel> {
        return withContext(Dispatchers.IO) {
            val result = getServerRolesUseCase(server.id)

            result.fold(
                onSuccess = { roles ->
                    if (ids == null) {
                        roles.map { role ->
                            RoleMiniCheckboxUiModel(
                                id = role.id,
                                title = role.name,
                                color = Color(role.color.toColorInt()),
                                isChosen = true
                            )
                        }
                    } else {
                        roles.map { role ->
                            RoleMiniCheckboxUiModel(
                                id = role.id,
                                title = role.name,
                                color = Color(role.color.toColorInt()),
                                isChosen = ids.contains(role.id)
                            )
                        }
                    }
                },
                onFailure = {
                    emptyList<RoleMiniCheckboxUiModel>()
                }
            )
        }
    }

    fun sendServerInvitation(
        userId: String,
        serverId: String,
    ) {
        viewModelScope.launch {
            val result = sendServerInvitationUseCase(userId, serverId)

            _sendServerInvitationEvent.value = result.fold(
                onSuccess = {
                    SendServerInvitationEvent.Success(userId)
                },
                onFailure = { error ->
                    SendServerInvitationEvent.Error("Ошибка при инициации приглашения. ${error.message}")
                }
            )
        }
    }

    fun setSentInvitation(userId: String) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is ServerCardUiState.Success) {
                val updatedData = currentState.data.copy(
                    friendsNotInServer = currentState.data.friendsNotInServer.map {
                        if (it.id == userId) {
                            it.copy(sent = true)
                        } else {
                            it
                        }
                    }
                )
                _uiState.value = ServerCardUiState.Success(updatedData)
            }
        }
    }

    fun loadCheckboxFriends(server: ServerExpandedUiModel) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success && currentState.data.newChannelMembers.isEmpty()) {
            val updatedData = currentState.data.copy(
                newChannelMembers = server.members.map { member ->
                    FriendCheckboxUiModel(
                        id = member.id,
                        name = member.name,
                        nickname = member.nickname,
                        status = member.status,
                        avatarUrl = member.avatarUrl,
                        isChosen = false
                    )
                }
            )
            _uiState.value = ServerCardUiState.Success(updatedData)
        }
    }

    fun loadCheckboxRoles(server: ServerExpandedUiModel) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success && currentState.data.newChannelRoles.isEmpty()) {
            viewModelScope.launch {
                val result = getServerRolesUseCase(server.id)

                result.fold(
                    onSuccess = { roles ->
                        val updatedData = currentState.data.copy(
                            newChannelRoles = roles.map { role ->
                                RoleMiniCheckboxUiModel(
                                    id = role.id,
                                    title = role.name,
                                    color = Color(role.color.toColorInt()),
                                    isChosen = false,
                                )
                            }
                        )
                        _uiState.value = ServerCardUiState.Success(updatedData)
                    },
                    onFailure = {}
                )
            }
        }
    }

    fun markAsReadAll() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is ServerCardUiState.Success) {
                val data = currentState.data

                for (channel in data.chosenServer.textChannels) {
                    markChatAsReadUseCase(channel.id)
                }
            }
        }
    }

    fun markAsRead(chatId: String) {
        viewModelScope.launch {
            markChatAsReadUseCase(chatId)
        }
    }

    fun loadChosenUser(userId: String) {
        viewModelScope.launch {
            val result = getUserInfoExtendedUseCase(userId)

            _loadChosenUserEvent.value = result.fold(
                onSuccess = { user ->
                    val currentState = _uiState.value
                    if (currentState is ServerCardUiState.Success) {
                        val updatedData = currentState.data.copy(
                            chosenUser = user.toUi()
                        )
                        _uiState.value = ServerCardUiState.Success(updatedData)
                    }
                    LoadChosenUserEvent.SuccessLoad
                },
                onFailure = {
                    LoadChosenUserEvent.Error(
                        ("Ошибка при загрузке информации о пользователе. " + it.message)
                    )
                }
            )
        }
    }

    fun loadChosenUserCommonServers(userId: String) {
        viewModelScope.launch {
            val result = getChosenUserCommonServersUseCase(userId)

            _loadChosenUserCommonServersEvent.value = result.fold(
                onSuccess = { servers ->
                    val currentState = _uiState.value
                    if (currentState is ServerCardUiState.Success) {
                        val updatedData = currentState.data.copy(
                            chosenUserCommonServers = servers.map { it.toUi() }
                        )
                        _uiState.value = ServerCardUiState.Success(updatedData)
                    }
                    LoadChosenUserCommonServersEvent.SuccessLoad
                },
                onFailure = {
                    LoadChosenUserCommonServersEvent.Error(
                        ("Ошибка при загрузке информации об общих серверах с пользователем. " + it.message)
                    )
                }
            )
        }
    }

    fun resetMembersAndRoles() {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            val updatedData = currentState.data.copy(
                newChannelMembers = listOf(),
                newChannelRoles = listOf()
            )
            _uiState.value = ServerCardUiState.Success(updatedData)
        }
    }

    fun onToggleRole(role: RoleMiniCheckboxUiModel) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            val updatedRoles = currentState.data.newChannelRoles.map { r ->
                if (r.id == role.id) {
                    r.copy(isChosen = !r.isChosen)
                } else {
                    r
                }
            }

            _uiState.value = currentState.copy(
                data = currentState.data.copy(newChannelRoles = updatedRoles)
            )
        }
    }

    fun onToggleRoleEditChannel(role: RoleMiniCheckboxUiModel) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            val updatedRoles = currentState.data.editChannel.roles.map { r ->
                if (r.id == role.id) {
                    r.copy(isChosen = !r.isChosen)
                } else {
                    r
                }
            }

            _uiState.value = currentState.copy(
                data = currentState.data.copy(editChannel = currentState.data.editChannel.copy(roles = updatedRoles))
            )

            viewModelScope.launch {
                if (role.isChosen) {
                    val result = assignChannelPermissionUseCase(
                        currentState.data.chosenServer.id,
                        currentState.data.editChannel.id,
                        role.id
                    )
                    result.fold(
                        onSuccess = {},
                        onFailure = {
                            errorHandler.handleError("Не удалось назначить роль в канале")
                            val revertedRoles = updatedRoles.map { r ->
                                if (r.id == role.id) r.copy(isChosen = !r.isChosen) else r
                            }
                            _uiState.value = currentState.copy(
                                data = currentState.data.copy(
                                    editChannel = currentState.data.editChannel.copy(
                                        roles = revertedRoles
                                    )
                                )
                            )
                        }
                    )
                } else {
                    val result = deleteChannelPermissionUseCase(
                        currentState.data.chosenServer.id,
                        currentState.data.editChannel.id,
                        role.id
                    )
                    result.fold(
                        onSuccess = { },
                        onFailure = {
                            errorHandler.handleError("Не удалось удалить роль из канала")
                            val revertedRoles = updatedRoles.map { r ->
                                if (r.id == role.id) r.copy(isChosen = !r.isChosen) else r
                            }
                            _uiState.value = currentState.copy(
                                data = currentState.data.copy(
                                    editChannel = currentState.data.editChannel.copy(
                                        roles = revertedRoles
                                    )
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    fun loadUserData() {
        viewModelScope.launch {
            val result = loadUserInfoUseCase()

            _loadUserInfoEvent.value = result.fold(
                onSuccess = { info ->
                    val currentState = _uiState.value
                    if (currentState is ServerCardUiState.Success) {
                        val updatedData = currentState.data.copy(
                            currentUser = ServerCardUiModel.CurrentUserUiModel(
                                id = info.id,
                                username = info.nickname,
                                name = info.username,
                                avatarUrl = info.avatarUrl,
                                status = info.status.toStatusPresentation(),
                                email = info.email,
                                description = info.description
                            )
                        )

                        _uiState.value = ServerCardUiState.Success(updatedData)
                    }
                    LoadUserDataEvent.SuccessLoad

                },
                onFailure = {
                    LoadUserDataEvent.Error(
                        ("Ошибка при загрузке профиля. " + it.message)
                    )
                }
            )
        }
    }

    fun extractMainColor(bitmap: Bitmap): Color {
        return colorService.extractMainColor(bitmap)
    }


    fun handleError(message: String) {
        errorHandler.handleError(
            message = message
        )
    }


    fun resetCreateChannelEvent() {
        _createChannelEvent.value = null
    }

    fun resetDeleteChannelEvent() {
        _deleteChannelEvent.value = null
    }

    fun resetDeleteServerEvent() {
        _deleteServerEvent.value = null
    }

    fun resetGetServerInfoEvent() {
        _getServerInfoEvent.value = null
    }

    fun resetGetFriendsNotInServerEvent() {
        _getFriendsNotInServerEvent.value = null
    }

    fun resetLoadChosenUserEvent() {
        _loadChosenUserEvent.value = null
    }

    fun resetLoadChosenUserCommonServersEvent() {
        _loadChosenUserCommonServersEvent.value = null
    }

    fun resetLoadChosenChannelEvent() {
        _loadChosenChannelEvent.value = null
    }

    fun resetPatchChannelEvent() {
        _patchChannelEvent.value = null
    }

    fun resetGetTokenEvent() {
        _getTokenEvent.value = null
    }

    fun resetGetVoiceChannelTokenEvent() {
        _getVoiceChannelTokenEvent.value = null
    }

    fun resetRespondToFriendRequestEvent() {
        _respondToFriendshipRequestEvent.value = null
    }

    fun resetCreateFriendRequestEvent() {
        _createFriendRequestEvent.value = null
    }

    fun resetDeleteFriendshipEvent() {
        _deleteFriendshipEvent.value = null
    }

    fun resetLoadUserInfoEvent() {
        _loadUserInfoEvent.value = null
    }

    fun resetLeaveServerEvent() {
        _leaveServerEvent.value = null
    }

    fun resetStartChatEvent() {
        _startChatEvent.value = null
    }
}