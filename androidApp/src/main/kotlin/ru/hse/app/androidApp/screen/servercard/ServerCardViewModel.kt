package ru.hse.app.androidApp.screen.servercard

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService
import ru.hse.app.androidApp.domain.usecase.friends.GetChosenUserCommonServersUseCase
import ru.hse.app.androidApp.domain.usecase.friends.GetUserInfoExtendedUseCase
import ru.hse.app.androidApp.domain.usecase.channel.CreateChannelUseCase
import ru.hse.app.androidApp.domain.usecase.servers.CreateServerRoleUseCase
import ru.hse.app.androidApp.domain.usecase.servers.CreateServerUseCase
import ru.hse.app.androidApp.domain.usecase.channel.DeleteChannelUseCase
import ru.hse.app.androidApp.domain.usecase.channel.GetChannelInfoUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerInvitationUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerMemberUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetFriendsNotInServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInfoUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInvitationsUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerRolesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerUserRolesUseCase
import ru.hse.app.androidApp.domain.usecase.channel.PatchChannelPropertiesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchServerOwnerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchServerPropertiesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.SearchMembersUseCase
import ru.hse.app.androidApp.domain.usecase.servers.SendServerInvitationUseCase
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.TextChannelUiModel
import ru.hse.app.androidApp.ui.entity.model.VoiceChannelUiModel
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadChosenUserCommonServersEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadChosenUserEvent
import ru.hse.app.androidApp.ui.entity.model.servers.ServerCardUiModel
import ru.hse.app.androidApp.ui.entity.model.servers.ServerCardUiState
import ru.hse.app.androidApp.ui.entity.model.servers.ServerExpandedUiModel
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateServerRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerInvitationEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerMemberEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetFriendsNotInServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInvitationsEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerUserRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetUserServersEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.JoinServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.PatchServerOwnerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.SendServerInvitationEvent
import ru.hse.app.androidApp.ui.entity.model.servers.toUi
import ru.hse.app.androidApp.ui.entity.model.toInvitationUi
import ru.hse.app.androidApp.ui.entity.model.toUi
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ServerCardViewModel @Inject constructor(
    private val createChannelUseCase: CreateChannelUseCase,
    private val createServerRoleUseCase: CreateServerRoleUseCase,
    private val createServerUseCase: CreateServerUseCase,

    private val deleteChannelUseCase: DeleteChannelUseCase,
    private val deleteServerInvitationUseCase: DeleteServerInvitationUseCase,
    private val deleteServerMemberUseCase: DeleteServerMemberUseCase,
    private val deleteServerUseCase: DeleteServerUseCase,

    private val getServerInfoUseCase: GetServerInfoUseCase,
    private val getServerInvitationsUseCase: GetServerInvitationsUseCase,
    private val getServerRolesUseCase: GetServerRolesUseCase,
    private val getServerUserRolesUseCase: GetServerUserRolesUseCase,
    private val getFriendsNotInServerUseCase: GetFriendsNotInServerUseCase,
    private val getUserInfoExtendedUseCase: GetUserInfoExtendedUseCase,
    private val getChosenUserCommonServersUseCase: GetChosenUserCommonServersUseCase,
    private val getChannelInfoUseCase: GetChannelInfoUseCase,

    private val patchChannelPropertiesUseCase: PatchChannelPropertiesUseCase,
    private val patchServerOwnerUseCase: PatchServerOwnerUseCase,
    private val patchServerPropertiesUseCase: PatchServerPropertiesUseCase,
    private val patchServerRoleUseCase: PatchServerOwnerUseCase,

    private val sendServerInvitationUseCase: SendServerInvitationUseCase,
    private val searchMembersUseCase: SearchMembersUseCase,

    private val dataManager: DataManager,
    private val photoConverterService: PhotoConverterService,
    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val imageLoader: ImageLoader

) : ViewModel() {

    val isDarkTheme = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ServerCardUiState>(ServerCardUiState.Loading)
    val uiState: StateFlow<ServerCardUiState> = _uiState

    private val _createChannelEvent = MutableStateFlow<CreateChannelEvent?>(null)
    val createChannelEvent: StateFlow<CreateChannelEvent?> = _createChannelEvent

    private val _createServerEvent = MutableStateFlow<CreateServerEvent?>(null)
    val createServerEvent: StateFlow<CreateServerEvent?> = _createServerEvent

    private val _createServerRoleEvent = MutableStateFlow<CreateServerRoleEvent?>(null)
    val createServerRoleEvent: StateFlow<CreateServerRoleEvent?> = _createServerRoleEvent

    // Delete events
    private val _deleteChannelEvent = MutableStateFlow<DeleteChannelEvent?>(null)
    val deleteChannelEvent: StateFlow<DeleteChannelEvent?> = _deleteChannelEvent

    private val _deleteServerEvent = MutableStateFlow<DeleteServerEvent?>(null)
    val deleteServerEvent: StateFlow<DeleteServerEvent?> = _deleteServerEvent

    private val _deleteServerInvitationEvent = MutableStateFlow<DeleteServerInvitationEvent?>(null)
    val deleteServerInvitationEvent: StateFlow<DeleteServerInvitationEvent?> =
        _deleteServerInvitationEvent

    private val _deleteServerMemberEvent = MutableStateFlow<DeleteServerMemberEvent?>(null)
    val deleteServerMemberEvent: StateFlow<DeleteServerMemberEvent?> = _deleteServerMemberEvent

    // Get events
    private val _getServerInfoEvent = MutableStateFlow<GetServerInfoEvent?>(null)
    val getServerInfoEvent: StateFlow<GetServerInfoEvent?> = _getServerInfoEvent

    private val _getServerInvitationsEvent = MutableStateFlow<GetServerInvitationsEvent?>(null)
    val getServerInvitationsEvent: StateFlow<GetServerInvitationsEvent?> =
        _getServerInvitationsEvent

    private val _getServerRolesEvent = MutableStateFlow<GetServerRolesEvent?>(null)
    val getServerRolesEvent: StateFlow<GetServerRolesEvent?> = _getServerRolesEvent

    private val _getServerUserRolesEvent = MutableStateFlow<GetServerUserRolesEvent?>(null)
    val getServerUserRolesEvent: StateFlow<GetServerUserRolesEvent?> = _getServerUserRolesEvent

    private val _getUserServersEvent = MutableStateFlow<GetUserServersEvent?>(null)
    val getUserServersEvent: StateFlow<GetUserServersEvent?> = _getUserServersEvent

    private val _getFriendsNotInServerEvent = MutableStateFlow<GetFriendsNotInServerEvent?>(null)
    val getFriendsNotInServerEvent: StateFlow<GetFriendsNotInServerEvent?> =
        _getFriendsNotInServerEvent

    private val _loadChosenUserEvent = MutableStateFlow<LoadChosenUserEvent?>(null)
    val loadChosenUserEvent: StateFlow<LoadChosenUserEvent?> = _loadChosenUserEvent

    private val _loadChosenUserCommonServersEvent =
        MutableStateFlow<LoadChosenUserCommonServersEvent?>(null)
    val loadChosenUserCommonServersEvent: StateFlow<LoadChosenUserCommonServersEvent?> =
        _loadChosenUserCommonServersEvent

    private val _joinServerEvent = MutableStateFlow<JoinServerEvent?>(null)
    val joinServerEvent: StateFlow<JoinServerEvent?> = _joinServerEvent

    private val _patchServerOwnerEvent = MutableStateFlow<PatchServerOwnerEvent?>(null)
    val patchServerOwnerEvent: StateFlow<PatchServerOwnerEvent?> = _patchServerOwnerEvent

    private val _sendServerInvitationEvent = MutableStateFlow<SendServerInvitationEvent?>(null)
    val sendServerInvitationEvent: StateFlow<SendServerInvitationEvent?> =
        _sendServerInvitationEvent

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
    val typeOfCreatingChannel = mutableStateOf("text")
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
            _uiState.value = currentState.copy( data = updatedData)
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
            _uiState.value = currentState.copy( data = updatedData)
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
            _uiState.value = currentState.copy( data = updatedData)
        }
    }

    fun onSearchMembersText(value: String) {
        searchMembersText.value = value
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            viewModelScope.launch {
                val result = searchMembersUseCase(currentState.data.chosenServer.members.map { it }, value)
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
        members: List<FriendCheckboxUiModel> = listOf(),
        roles: List<RoleMiniCheckboxUiModel> = listOf()
    ) {
        if (channelName.isEmpty()) {
            showToast("Название канала не может быть пустым")
            return
        }
        val roundLimit = limit.roundToInt()
        val membersId = members.filter { it.isChosen }.map { it.id }
        val rolesId = roles.filter { it.isChosen }.map { it.id }
        viewModelScope.launch {
            val data = CreateChannel(
                name = channelName,
                isPrivate = isPrivate,
                type = type,
                limit = if (roundLimit > 0f) roundLimit else null,
                members = membersId,
                roles = rolesId
            )
            val result = createChannelUseCase(serverId, data)

            _createChannelEvent.value = result.fold(
                onSuccess = {
                    CreateChannelEvent.SuccessCreate
                },
                onFailure = {
                    CreateChannelEvent.Error("Ошибка при создании канала. " + it.message)
                }
            )
        }
    }

    fun createServer(
        serverName: String,
        serverPhoto: Uri? = null,
    ) {
        if (serverName.isEmpty()) {
            showToast("Название сервера не может быть пустым")
            return
        }
        viewModelScope.launch {
            val result = createServerUseCase(serverName, serverPhoto)

            _createServerEvent.value = result.fold(
                onSuccess = { server ->
                    CreateServerEvent.SuccessCreate
                },
                onFailure = { error ->
                    CreateServerEvent.Error("Ошибка при создании сервера. ${error.message}")
                }
            )
        }
    }

    fun createServerRole(
        serverId: String,
        roleName: String,
        roleColor: String,
        members: List<String> = listOf()
    ) {
        viewModelScope.launch {
            val data = CreateRole(
                name = roleName,
                color = roleColor,
                members = members
            )
            val result = createServerRoleUseCase(serverId, data)

            _createServerRoleEvent.value = result.fold(
                onSuccess = { role ->
                    CreateServerRoleEvent.SuccessCreate
                },
                onFailure = { error ->
                    CreateServerRoleEvent.Error("Ошибка при создании роли. ${error.message}")
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

    fun deleteServerInvitation(
        serverId: String,
        invitationId: String
    ) {
        viewModelScope.launch {
            val result = deleteServerInvitationUseCase(serverId, invitationId)

            _deleteServerInvitationEvent.value = result.fold(
                onSuccess = {
                    DeleteServerInvitationEvent.SuccessDelete
                },
                onFailure = { error ->
                    DeleteServerInvitationEvent.Error("Ошибка при удалении приглашения. ${error.message}")
                }
            )
        }
    }

    fun deleteServerMember(
        serverId: String,
        memberId: String
    ) {
        viewModelScope.launch {
            val result = deleteServerMemberUseCase(serverId, memberId)

            _deleteServerMemberEvent.value = result.fold(
                onSuccess = {
                    DeleteServerMemberEvent.SuccessDelete
                },
                onFailure = { error ->
                    DeleteServerMemberEvent.Error("Ошибка при удалении участника. ${error.message}")
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

    fun getServerInvitations(serverId: String) {
        viewModelScope.launch {
            val result = getServerInvitationsUseCase(serverId)

            _getServerInvitationsEvent.value = result.fold(
                onSuccess = { invitations ->
                    //TODO
                    GetServerInvitationsEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetServerInvitationsEvent.Error("Ошибка при получении приглашений. ${error.message}")
                }
            )
        }
    }

    fun getServerRoles(serverId: String) {
        viewModelScope.launch {
            val result = getServerRolesUseCase(serverId)

            _getServerRolesEvent.value = result.fold(
                onSuccess = { roles ->
                    //TODO
                    GetServerRolesEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetServerRolesEvent.Error("Ошибка при получении ролей сервера. ${error.message}")
                }
            )
        }
    }

    fun getServerUserRoles(
        serverId: String,
        userId: String
    ) {
        viewModelScope.launch {
            val result = getServerUserRolesUseCase(serverId, userId)

            _getServerUserRolesEvent.value = result.fold(
                onSuccess = { roles ->
                    //TODO
                    GetServerUserRolesEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetServerUserRolesEvent.Error("Ошибка при получении ролей пользователя. ${error.message}")
                }
            )
        }
    }

    fun loadChosenChannel(server: ServerExpandedUiModel, channelId: String) {
        viewModelScope.launch {
            val result = getChannelInfoUseCase(server.id, channelId)
            // todo обновить значение ивента и при неуспешной загрузке не загружать страницу
            val currentState = _uiState.value

            result.fold(
                onSuccess = { channel ->
                    val checkboxRoles = idsToRolesCheckboxInChannel(server,channel.roles)
                    if (currentState is ServerCardUiState.Success) {
                        val updatedData = currentState.data.copy(
                            editChannel = currentState.data.editChannel.copy(
                                id = channel.id,
                                type = channel.type,
                                name = channel.name,
                                isPrivate = channel.isPrivate,
                                limit = channel.limit?.toFloat()?: 0f,
                                members = idsToFriendCheckboxInChannel(server,channel.members),
                                roles = checkboxRoles
                            )
                        )
                        _uiState.value = ServerCardUiState.Success(updatedData)
                    }
                },
                onFailure = {

                }
            )
        }
    }

    fun idsToFriendCheckboxInChannel(server: ServerExpandedUiModel, ids: List<String>) : List<FriendCheckboxUiModel> {
        return server.members.map { member ->
            FriendCheckboxUiModel(
                id = member.id,
                name = member.name,
                nickname = member.nickname,
                status = member.status,
                avatarUrl = member.avatarUrl,
                isChosen = ids.contains(member.id)
            )
        }
    }

    suspend fun idsToRolesCheckboxInChannel(server: ServerExpandedUiModel, ids: List<String>): List<RoleMiniCheckboxUiModel> {
        return withContext(Dispatchers.IO) {
            val result = getServerRolesUseCase(server.id)

            result.fold(
                onSuccess = { roles ->
                    roles.map { role ->
                        RoleMiniCheckboxUiModel(
                            id = role.id,
                            title = role.name,
                            color = Color(role.color.toColorInt()),
                            isChosen = ids.contains(role.id)
                        )
                    }
                },
                onFailure = {
                    emptyList<RoleMiniCheckboxUiModel>()
                }
            )
        }
    }


    fun patchServerOwner(
        serverId: String,
        newOwnerId: String
    ) {
        viewModelScope.launch {
            val result = patchServerOwnerUseCase(serverId, newOwnerId)

            _patchServerOwnerEvent.value = result.fold(
                onSuccess = {
                    //TODO
                    PatchServerOwnerEvent.SuccessPatch
                },
                onFailure = { error ->
                    PatchServerOwnerEvent.Error("Ошибка при передаче прав владельца. ${error.message}")
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
                    //TODO
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


    fun onToggleFriend(user: FriendCheckboxUiModel) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            val updatedMembers = currentState.data.newChannelMembers.map { friend ->
                if (friend.id == user.id) {
                    friend.copy(isChosen = !friend.isChosen)
                } else {
                    friend
                }
            }
            _uiState.value = currentState.copy(
                data = currentState.data.copy(newChannelMembers = updatedMembers)
            )
        }
    }

    fun onToggleFriendEditChannel(user: FriendCheckboxUiModel) {
        val currentState = _uiState.value
        if (currentState is ServerCardUiState.Success) {
            //todo сразу обновлять бэк
            val updatedMembers = currentState.data.editChannel.members.map { friend ->
                if (friend.id == user.id) {
                    friend.copy(isChosen = !friend.isChosen)
                } else {
                    friend
                }
            }
            _uiState.value = currentState.copy(
                data = currentState.data.copy(editChannel = currentState.data.editChannel.copy(members = updatedMembers))
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
        //todo сразу обновлять бэк
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
        }
    }


    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toastManager.showToast(
            message = message,
            duration = duration
        )
    }


    fun resetCreateChannelEvent() {
        _createChannelEvent.value = null
    }

    fun resetCreateServerEvent() {
        _createServerEvent.value = null
    }

    fun resetCreateServerRoleEvent() {
        _createServerRoleEvent.value = null
    }

    fun resetDeleteChannelEvent() {
        _deleteChannelEvent.value = null
    }

    fun resetDeleteServerEvent() {
        _deleteServerEvent.value = null
    }

    fun resetDeleteServerInvitationEvent() {
        _deleteServerInvitationEvent.value = null
    }

    fun resetDeleteServerMemberEvent() {
        _deleteServerMemberEvent.value = null
    }

    fun resetGetServerInfoEvent() {
        _getServerInfoEvent.value = null
    }

    fun resetGetServerInvitationsEvent() {
        _getServerInvitationsEvent.value = null
    }

    fun resetGetServerRolesEvent() {
        _getServerRolesEvent.value = null
    }

    fun resetGetServerUserRolesEvent() {
        _getServerUserRolesEvent.value = null
    }

    fun resetGetUserServersEvent() {
        _getUserServersEvent.value = null
    }

    fun resetJoinServerEvent() {
        _joinServerEvent.value = null
    }

    fun resetPatchServerOwnerEvent() {
        _patchServerOwnerEvent.value = null
    }

    fun resetSendServerInvitationEvent() {
        _sendServerInvitationEvent.value = null
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
}