package ru.hse.app.androidApp.screen.servers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.domain.model.entity.CreateChannel
import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.model.entity.CreateServer
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService
import ru.hse.app.androidApp.domain.usecase.servers.CreateChannelUseCase
import ru.hse.app.androidApp.domain.usecase.servers.CreateServerRoleUseCase
import ru.hse.app.androidApp.domain.usecase.servers.CreateServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteChannelUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerInvitationUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerMemberUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInfoUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInvitationsUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerRolesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerUserRolesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetUserServersUseCase
import ru.hse.app.androidApp.domain.usecase.servers.JoinServerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchChannelPropertiesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchServerOwnerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchServerPropertiesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.SendServerInvitationUseCase
import ru.hse.app.androidApp.ui.entity.model.profile.events.RespondToFriendRequestEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateServerRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerInvitationEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerMemberEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInvitationsEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerUserRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetUserServersEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.JoinServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.PatchServerOwnerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.SendServerInvitationEvent
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class ServersViewModel @Inject constructor(
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
    private val getUserServersUseCase: GetUserServersUseCase,

    private val joinServerUseCase: JoinServerUseCase,

    private val patchChannelPropertiesUseCase: PatchChannelPropertiesUseCase,
    private val patchServerOwnerUseCase: PatchServerOwnerUseCase,
    private val patchServerPropertiesUseCase: PatchServerPropertiesUseCase,
    private val patchServerRoleUseCase: PatchServerOwnerUseCase,

    private val sendServerInvitationUseCase: SendServerInvitationUseCase,

    private val photoConverterService: PhotoConverterService,
    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val imageLoader: ImageLoader

) : ViewModel() {

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

    private val _joinServerEvent = MutableStateFlow<JoinServerEvent?>(null)
    val joinServerEvent: StateFlow<JoinServerEvent?> = _joinServerEvent

    private val _patchServerOwnerEvent = MutableStateFlow<PatchServerOwnerEvent?>(null)
    val patchServerOwnerEvent: StateFlow<PatchServerOwnerEvent?> = _patchServerOwnerEvent

    private val _sendServerInvitationEvent = MutableStateFlow<SendServerInvitationEvent?>(null)
    val sendServerInvitationEvent: StateFlow<SendServerInvitationEvent?> = _sendServerInvitationEvent

    fun createChannel (
        serverId: String,
        channelName: String,
        isPrivate: Boolean,
        type: String,
        limit: Int? = null,
        members: List<String> = listOf(),
        roles: List<String> = listOf()
    ) {
        viewModelScope.launch {
            val data = CreateChannel(
                name = channelName,
                isPrivate = isPrivate,
                type = type,
                limit = limit,
                members = members,
                roles = roles
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
        serverPhoto: String? = null,
    ) {
        viewModelScope.launch {
            val data = CreateServer(
                name = serverName,
                photoUrl = serverPhoto
            )
            val result = createServerUseCase(data)

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
                    //TODO
                    GetServerInfoEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetServerInfoEvent.Error("Ошибка при получении информации о сервере. ${error.message}")
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

    fun getUserServers() {
        viewModelScope.launch {
            val result = getUserServersUseCase()

            _getUserServersEvent.value = result.fold(
                onSuccess = { servers ->
                    //TODO
                    GetUserServersEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetUserServersEvent.Error("Ошибка при получении списка серверов. ${error.message}")
                }
            )
        }
    }

    fun joinServer() {
        //TODO
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
                    SendServerInvitationEvent.Success
                },
                onFailure = { error ->
                    SendServerInvitationEvent.Error("Ошибка при инициации приглашения. ${error.message}")
                }
            )
        }
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

    fun resetJoinServer() {
        _joinServerEvent.value = null
    }

    fun resetPatchServerOwnerEvent() {
        _patchServerOwnerEvent.value = null
    }

    fun resetSendServerInvitation() {
        _sendServerInvitationEvent.value = null
    }

}