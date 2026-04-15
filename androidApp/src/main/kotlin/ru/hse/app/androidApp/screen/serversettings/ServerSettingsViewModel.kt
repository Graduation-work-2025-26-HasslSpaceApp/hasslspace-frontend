package ru.hse.app.androidApp.screen.serversettings

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.model.entity.CreateRole
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService
import ru.hse.app.androidApp.domain.usecase.invitations.DeleteServerInvitationUseCase
import ru.hse.app.androidApp.domain.usecase.invitations.GetServerInvitationsUseCase
import ru.hse.app.androidApp.domain.usecase.roles.AssignRoleUseCase
import ru.hse.app.androidApp.domain.usecase.roles.CreateServerRoleUseCase
import ru.hse.app.androidApp.domain.usecase.roles.DeleteServerRoleUseCase
import ru.hse.app.androidApp.domain.usecase.roles.GetServerRolesUseCase
import ru.hse.app.androidApp.domain.usecase.roles.PatchServerRoleUseCase
import ru.hse.app.androidApp.domain.usecase.roles.RevokeRoleUseCase
import ru.hse.app.androidApp.domain.usecase.servers.DeleteServerMemberUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInfoUseCase
import ru.hse.app.androidApp.domain.usecase.servers.PatchServerPropertiesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.SearchMembersUseCase
import ru.hse.app.androidApp.domain.usecase.servers.UpdateServerOwnerUseCase
import ru.hse.app.androidApp.domain.usecase.servers.UpdateServerPhotoUseCase
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCountUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.servers.events.AssignRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateServerRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteInvitationEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerMemberEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInvitationsEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.LoadChosenServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.PatchServerOwnerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.PatchServerPropertiesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.PatchServerRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.RevokeRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.UpdateServerPhotoEvent
import ru.hse.app.androidApp.ui.entity.model.serversettings.ServerSettingsUiModel
import ru.hse.app.androidApp.ui.entity.model.serversettings.ServerSettingsUiState
import ru.hse.app.androidApp.ui.entity.model.serversettings.toRoleInfoUiModel
import ru.hse.app.androidApp.ui.entity.model.serversettings.toServerSettingsUiModel
import ru.hse.app.androidApp.ui.entity.model.toRoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.toRoleMiniIfChosen
import ru.hse.app.androidApp.ui.entity.model.toUi
import ru.hse.app.androidApp.ui.errorhandling.ErrorHandler
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class ServerSettingsViewModel @Inject constructor(
    private val createServerRoleUseCase: CreateServerRoleUseCase,

    private val getServerInfoUseCase: GetServerInfoUseCase,
    private val getServerInvitationsUseCase: GetServerInvitationsUseCase,
    private val getServerRolesUseCase: GetServerRolesUseCase,

    private val searchMembersUseCase: SearchMembersUseCase,

    private val deleteInvitationUseCase: DeleteServerInvitationUseCase,
    private val deleteServerMemberUseCase: DeleteServerMemberUseCase,
    private val deleteRoleUseCase: DeleteServerRoleUseCase,

    private val updateServerOwnerUseCase: UpdateServerOwnerUseCase,
    private val updateServerPhotoUseCase: UpdateServerPhotoUseCase,
    private val patchServerPropertiesUseCase: PatchServerPropertiesUseCase,

    private val assignRoleUseCase: AssignRoleUseCase,
    private val revokeRoleUseCase: RevokeRoleUseCase,

    private val errorHandler: ErrorHandler,

    private val patchServerRoleUseCase: PatchServerRoleUseCase,

    private val photoConverterService: PhotoConverterService,


    private val dataManager: DataManager,
    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val imageLoader: ImageLoader
) : ViewModel() {

    val isDarkTheme = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ServerSettingsUiState>(ServerSettingsUiState.Loading)
    val uiState: StateFlow<ServerSettingsUiState> = _uiState

    private val _getServerInfoEvent = MutableStateFlow<GetServerInfoEvent?>(null)
    val getServerInfoEvent: StateFlow<GetServerInfoEvent?> = _getServerInfoEvent

    private val _getServerInvitationsEvent = MutableStateFlow<GetServerInvitationsEvent?>(null)
    val getServerInvitationsEvent: StateFlow<GetServerInvitationsEvent?> =
        _getServerInvitationsEvent

    private val _getServerRolesEvent = MutableStateFlow<GetServerRolesEvent?>(null)
    val getServerRolesEvent: StateFlow<GetServerRolesEvent?> = _getServerRolesEvent

    private val _loadChosenServerRolesEvent = MutableStateFlow<LoadChosenServerRolesEvent?>(null)
    val loadChosenServerRolesEvent: StateFlow<LoadChosenServerRolesEvent?> = _loadChosenServerRolesEvent

    private val _deleteInvitationEvent = MutableStateFlow<DeleteInvitationEvent?>(null)
    val deleteInvitationEvent: StateFlow<DeleteInvitationEvent?> = _deleteInvitationEvent

    private val _deleteServerMemberEvent = MutableStateFlow<DeleteServerMemberEvent?>(null)
    val deleteServerMemberEvent: StateFlow<DeleteServerMemberEvent?> = _deleteServerMemberEvent

    private val _assignRoleEvent = MutableStateFlow<AssignRoleEvent?>(null)
    val assignRoleEvent: StateFlow<AssignRoleEvent?> = _assignRoleEvent

    private val _revokeRoleEvent = MutableStateFlow<RevokeRoleEvent?>(null)
    val revokeRoleEvent: StateFlow<RevokeRoleEvent?> = _revokeRoleEvent

    private val _createServerRoleEvent = MutableStateFlow<CreateServerRoleEvent?>(null)
    val createServerRoleEvent: StateFlow<CreateServerRoleEvent?> = _createServerRoleEvent

    private val _patchServerOwnerEvent = MutableStateFlow<PatchServerOwnerEvent?>(null)
    val patchServerOwnerEvent: StateFlow<PatchServerOwnerEvent?> = _patchServerOwnerEvent

    private val _patchServerRoleEvent = MutableStateFlow<PatchServerRoleEvent?>(null)
    val patchServerRoleEvent: StateFlow<PatchServerRoleEvent?> = _patchServerRoleEvent

    private val _deleteRoleEvent = MutableStateFlow<DeleteRoleEvent?>(null)
    val deleteRoleEvent: StateFlow<DeleteRoleEvent?> = _deleteRoleEvent

    private val _updateServerPhotoEvent = MutableStateFlow<UpdateServerPhotoEvent?>(null)
    val updateServerPhotoEvent: StateFlow<UpdateServerPhotoEvent?> = _updateServerPhotoEvent

    private val _patchServerPropertiesEvent = MutableStateFlow<PatchServerPropertiesEvent?>(null)
    val patchServerPropertiesEvent: StateFlow<PatchServerPropertiesEvent?> =
        _patchServerPropertiesEvent

    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
    val serverName = mutableStateOf("")

    val searchMembersText = mutableStateOf("")
    val searchedMembers = mutableStateListOf<ServerMemberUiModel>()

    val chosenMember: MutableState<ServerMemberUiModel?> = mutableStateOf(null)
    val showEditMember = mutableStateOf(false)

    val showChooseRoles = mutableStateOf(false)
    val showDeleteMemberDialog = mutableStateOf(false)
    val showTransferRights = mutableStateOf(false)

    // New role screen
    val showNewRoleScreen = mutableStateOf(false)
    val showColorPicker = mutableStateOf(false)

    // Edit Role
    val showEditRole = mutableStateOf(false)
    val showColorPickerEdit = mutableStateOf(false)
    val originalEditRoleTitle = mutableStateOf("")
    val showDeleteRoleDialog = mutableStateOf(false)

    fun onSearchMembersText(value: String) {
        searchMembersText.value = value
        val currentState = _uiState.value
        if (currentState is ServerSettingsUiState.Success) {
            viewModelScope.launch {
                val result = searchMembersUseCase(currentState.data.members.map { it }, value)
                searchedMembers.clear()
                searchedMembers.addAll(result)
            }
        }
    }

    fun onEditRoleNameChanged(value: String) {
        val currentState = _uiState.value
        if (currentState is ServerSettingsUiState.Success) {
            val updatedData = currentState.data.copy(
                editedRole = currentState.data.editedRole.copy(
                    name = value
                )
            )
            _uiState.value = ServerSettingsUiState.Success(updatedData)
        }
    }

    fun onEditRoleColorChanged(color: Color) {
        val currentState = _uiState.value
        if (currentState is ServerSettingsUiState.Success) {
            val updatedData = currentState.data.copy(
                editedRole = currentState.data.editedRole.copy(
                    color = color
                )
            )
            _uiState.value = ServerSettingsUiState.Success(updatedData)
        }
    }

    fun onToggleEditRoleMember(serverId: String, roleId: String, tapped: FriendCheckboxUiModel) {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState is ServerSettingsUiState.Success) {

                try {
                    if (!tapped.isChosen) {
                        val result = assignRoleUseCase(
                            serverId = serverId,
                            targetUserId = tapped.id,
                            roleId = roleId
                        )
                        _assignRoleEvent.value = result.fold(
                            onSuccess = {
                                AssignRoleEvent.Success
                            },

                            onFailure = { error ->
                                AssignRoleEvent.Error("Ошибка при выдаче роли. ${error.message}")
                            }
                        )
                    } else {
                        val result = revokeRoleUseCase(
                            serverId = serverId,
                            targetUserId = tapped.id,
                            roleId = roleId
                        )

                        _revokeRoleEvent.value = result.fold(
                            onSuccess = {
                                RevokeRoleEvent.Success
                            },

                            onFailure = { error ->
                                RevokeRoleEvent.Error("Ошибка при снятии роли. ${error.message}")
                            }
                        )
                    }

                    val updatedNewRoleMembers = currentState.data.editedRole.members.map { member ->
                        if (member.id == tapped.id) {
                            member.copy(isChosen = !member.isChosen)
                        } else {
                            member
                        }
                    }

                    val updatedData = currentState.data.copy(
                        editedRole = currentState.data.editedRole.copy(
                            members = updatedNewRoleMembers
                        )
                    )

                    _uiState.value = ServerSettingsUiState.Success(updatedData)

                } catch (e: Exception) {
                    // todo ловить и показывать ошибку
                    // _uiState.value = ServerSettingsUiState.Error(e.message)
                }
            }
        }
    }

    fun onNewRoleNameChanged(value: String) {
        val currentState = _uiState.value
        if (currentState is ServerSettingsUiState.Success) {
            val updatedData = currentState.data.copy(
                newRole = currentState.data.newRole.copy(
                    name = value
                )
            )
            _uiState.value = ServerSettingsUiState.Success(updatedData)
        }
    }

    fun onNewRoleColorChanged(color: Color) {
        val currentState = _uiState.value
        if (currentState is ServerSettingsUiState.Success) {
            val updatedData = currentState.data.copy(
                newRole = currentState.data.newRole.copy(
                    color = color
                )
            )
            _uiState.value = ServerSettingsUiState.Success(updatedData)
        }
    }

    fun loadEditRole(role: RoleMiniCountUiModel) {
        val currentState = _uiState.value

        if (currentState is ServerSettingsUiState.Success) {
            val roleInfo = currentState.data.roles.firstOrNull { it.id == role.id }

            roleInfo?.let {
                val updatedData = currentState.data.copy(
                    editedRole = currentState.data.editedRole.copy(
                        id = roleInfo.id,
                        name = roleInfo.name,
                        color = roleInfo.color,
                        members = currentState.data.editedRole.members.map { member ->
                            if (roleInfo.members.any { it.id == member.id }) {
                                member.copy(isChosen = true)
                            } else {
                                member
                            }
                        }
                    )
                )
                originalEditRoleTitle.value = roleInfo.name
                _uiState.value = ServerSettingsUiState.Success(updatedData)
            }

        }
    }

    fun saveEditedRole(serverId: String, roleId: String) {

        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState is ServerSettingsUiState.Success) {
                val data = currentState.data

                if (data.editedRole.name.isEmpty()) {
                    errorHandler("Название роли не может быть пустым")
                    return@launch
                }

                val result = patchServerRoleUseCase(
                    serverId = serverId,
                    roleId = roleId,
                    name = data.editedRole.name,
                    position = null,
                    color = "#${Integer.toHexString(data.editedRole.color.toArgb())}"
                )

                _patchServerRoleEvent.value = result.fold(
                    onSuccess = {
                        originalEditRoleTitle.value = ""
                        PatchServerRoleEvent.Success
                    },

                    onFailure = { error ->
                        PatchServerRoleEvent.Error("Ошибка при изменении роли. ${error.message}")
                    }
                )
            }

        }
    }

    fun deleteRole(serverId: String, roleId: String) {
        viewModelScope.launch {
            val result = deleteRoleUseCase(serverId, roleId)

            _deleteRoleEvent.value = result.fold(
                onSuccess = {
                    DeleteRoleEvent.SuccessDelete
                },

                onFailure = { error ->
                    DeleteRoleEvent.Error("Ошибка при удалении роли. ${error.message}")
                }
            )
        }
    }

    fun deleteInvitation(invitationId: String) {
        viewModelScope.launch {
            val result = deleteInvitationUseCase(invitationId)
            _deleteInvitationEvent.value = result.fold(
                onSuccess = {
                    DeleteInvitationEvent.SuccessDelete
                },
                onFailure = { error ->
                    DeleteInvitationEvent.Error("Ошибка при удалении. ${error.message}")
                }
            )
        }
    }

    fun createServerRole(
        serverId: String,
        newRole: ServerSettingsUiModel.NewRoleUiModel
    ) {
        if (newRole.name.isEmpty()) {
            errorHandler("Название роли не может быть пустым")
            return
        }
        val colorInt = if (newRole.color != Color.Transparent) newRole.color else Color.LightGray
        viewModelScope.launch {
            val data = CreateRole(
                name = newRole.name,
                color = "#${Integer.toHexString(colorInt.toArgb())}",
            )
            val result = createServerRoleUseCase(serverId, data)

            _createServerRoleEvent.value = result.fold(
                onSuccess = { role ->
                    val currentState = _uiState.value

                    if (currentState is ServerSettingsUiState.Success) {
                        val updatedData = currentState.data.copy(
                            newRole = currentState.data.newRole.copy(
                                name = "",
                                color = Color.Transparent,
                            )
                        )

                        _uiState.value = ServerSettingsUiState.Success(updatedData)
                    }

                    CreateServerRoleEvent.SuccessCreate
                },
                onFailure = { error ->
                    CreateServerRoleEvent.Error("Ошибка при создании роли. ${error.message}")
                }
            )
        }
    }

    fun urlToUri(url: String?): Uri? {
        return photoConverterService.urlToUri(url)
    }

    fun getServerInfo(serverId: String) {
        viewModelScope.launch {
            val result = getServerInfoUseCase(serverId)

            _getServerInfoEvent.value = result.fold(
                onSuccess = { serverInfo ->
                    val serverUi = serverInfo.toServerSettingsUiModel()
                    _uiState.value = ServerSettingsUiState.Success(
                        data = serverUi
                    )

                    selectedImageUri.value = urlToUri(serverUi.photoUrl)

                    getServerRoles(serverId)
                    getServerInvitations(serverId)

                    serverName.value = serverUi.name

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

    fun getServerInvitations(serverId: String) {
        viewModelScope.launch {
            val result = getServerInvitationsUseCase(serverId)
            val currentState = _uiState.value

            _getServerInvitationsEvent.value = result.fold(
                onSuccess = { invitations ->
                    if (currentState is ServerSettingsUiState.Success) {
                        val updatedData = currentState.data.copy(
                            invitations = invitations.map { invitation -> invitation.toUi() }
                        )
                        _uiState.value = ServerSettingsUiState.Success(updatedData)
                    }
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
            val currentState = _uiState.value

            _getServerRolesEvent.value = result.fold(
                onSuccess = { roles ->
                    if (currentState is ServerSettingsUiState.Success) {
                        val updatedData = currentState.data.copy(
                            roles = roles.map { role -> role.toRoleInfoUiModel() }
                        )
                        _uiState.value = ServerSettingsUiState.Success(updatedData)
                    }
                    GetServerRolesEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetServerRolesEvent.Error("Ошибка при получении ролей сервера. ${error.message}")
                }
            )
        }
    }

    fun loadChosenRoles(serverId: String) {
        viewModelScope.launch {
            val result = getServerRolesUseCase(serverId)
            val currentState = _uiState.value

            _loadChosenServerRolesEvent.value = result.fold(
                onSuccess = { roles ->
                    if (currentState is ServerSettingsUiState.Success) {
                        val userRolesIds =
                            chosenMember.value?.allRoles?.map { it.id }?.toSet() ?: emptySet()

                        val updatedData = currentState.data.copy(
                            chosenRoles = roles.map { role ->
                                role.toRoleMiniCheckboxUiModel().copy(
                                    isChosen = userRolesIds.contains(role.id)
                                )
                            }
                        )
                        _uiState.value = ServerSettingsUiState.Success(updatedData)
                    }

                    LoadChosenServerRolesEvent.SuccessLoad
                },
                onFailure = { error ->
                    LoadChosenServerRolesEvent.Error("Ошибка при получении ролей сервера. ${error.message}")
                }
            )
        }
    }

    fun onToggleRole(serverId: String, tapped: RoleMiniCheckboxUiModel) {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState is ServerSettingsUiState.Success) {
                val member = chosenMember.value ?: return@launch

                try {
                    if (!tapped.isChosen) {
                        val result = assignRoleUseCase(
                            serverId = serverId,
                            targetUserId = member.id,
                            roleId = tapped.id
                        )

                        _assignRoleEvent.value = result.fold(
                            onSuccess = {
                                val updatedChosenRoles = currentState.data.chosenRoles.map { role ->
                                    if (role.id == tapped.id) {
                                        role.copy(isChosen = !role.isChosen)
                                    } else {
                                        role
                                    }
                                }

                                val updatedData = currentState.data.copy(
                                    chosenRoles = updatedChosenRoles
                                )

                                _uiState.value = ServerSettingsUiState.Success(updatedData)
                                AssignRoleEvent.Success
                            },
                            onFailure = {
                                AssignRoleEvent.Error("Ошибка при выдачи роли. ${it.message}")
                            })
                    } else {
                        val result = revokeRoleUseCase(
                            serverId = serverId,
                            targetUserId = member.id,
                            roleId = tapped.id
                        )

                        _revokeRoleEvent.value = result.fold(
                            onSuccess = {
                                val updatedChosenRoles = currentState.data.chosenRoles.map { role ->
                                    if (role.id == tapped.id) {
                                        role.copy(isChosen = !role.isChosen)
                                    } else {
                                        role
                                    }
                                }

                                val updatedData = currentState.data.copy(
                                    chosenRoles = updatedChosenRoles
                                )

                                _uiState.value = ServerSettingsUiState.Success(updatedData)
                                RevokeRoleEvent.Success
                            },
                            onFailure = {
                                RevokeRoleEvent.Error("Ошибка при снятии роли. ${it.message}")
                            })
                    }

                } catch (e: Exception) {
                    // todo ловить и показывать ошибку
                    // _uiState.value = ServerSettingsUiState.Error(e.message)
                }
            }
        }
    }

    fun clearChosenRoles() {
        val currentState = _uiState.value

        if (currentState is ServerSettingsUiState.Success) {
            val memberId = chosenMember.value?.id ?: return

            val updatedData = currentState.data.copy(
                members = currentState.data.members.map { member ->
                    if (member.id == memberId) {
                        val updatedMember =
                            member.copy(allRoles = currentState.data.chosenRoles.mapNotNull { it.toRoleMiniIfChosen() })
                        chosenMember.value = updatedMember
                        updatedMember
                    } else {
                        member
                    }
                },
                chosenRoles = listOf()
            )

            _uiState.value = ServerSettingsUiState.Success(updatedData)
        }
    }

    fun transferRightsToMember(serverId: String, member: ServerMemberUiModel) {
        viewModelScope.launch {
            val result = updateServerOwnerUseCase(member.id, serverId)

            _patchServerOwnerEvent.value = result.fold(
                onSuccess = {
                    PatchServerOwnerEvent.SuccessPatch
                },

                onFailure = { error ->
                    PatchServerOwnerEvent.Error("Ошибка при передаче прав. ${error.message}")
                },
            )
        }
    }

    fun deleteMember(serverId: String, member: ServerMemberUiModel) {
        viewModelScope.launch {
            val result = deleteServerMemberUseCase(serverId, member.id)

            _deleteServerMemberEvent.value = result.fold(
                onSuccess = {
                    DeleteServerMemberEvent.SuccessDelete
                },

                onFailure = { error ->
                    DeleteServerMemberEvent.Error("Ошибка при удалении участника. ${error.message}")
                },
            )
        }
    }

    fun isEnabledChange(value: String): Boolean {
        val currentState = _uiState.value
        if (currentState is ServerSettingsUiState.Success) {
            return value.isNotEmpty() && (value != currentState.data.name)
        }
        return false
    }

    fun onEditedServernameChanged(value: String) {
        serverName.value = value
    }

    fun onSaveEditedServername(serverId: String, value: String) {
        if (value.isEmpty()) {
            errorHandler.handleError("Название не может быть пустым")
            return
        }
        viewModelScope.launch {
            val result = patchServerPropertiesUseCase(serverId, value, null)

            _patchServerPropertiesEvent.value = result.fold(
                onSuccess = {
                    PatchServerPropertiesEvent.Success
                },

                onFailure = { error ->
                    PatchServerPropertiesEvent.Error("Ошибка при изменении данных сервера. " + error.message)
                }
            )
        }
    }

    fun saveServerPhoto(uriValue: Uri?) {
        val currentState = _uiState.value
        if (currentState is ServerSettingsUiState.Success) {
            val data = currentState.data
            viewModelScope.launch {
                uriValue?.let { uri ->
                    val photoResultResponse =
                        updateServerPhotoUseCase(
                            data.id,
                            uri,
                            data.photoUrl.takeIf { it.isNotEmpty() })
                    _updateServerPhotoEvent.value = photoResultResponse.fold(
                        onSuccess = { url ->
                            val updatedData = currentState.data.copy(
                                photoUrl = url
                            )
                            _uiState.value = ServerSettingsUiState.Success(updatedData)
                            UpdateServerPhotoEvent.Success
                        },
                        onFailure = {
                            UpdateServerPhotoEvent.Error("Ошибка при сохранении фото. " + it.message)
                        }
                    )
                }
            }
        }
    }

    fun onSelectedImageUri(uri: Uri?) {
        selectedImageUri.value = uri
        saveServerPhoto(uri)
    }

    fun errorHandler(message: String, duration: Int = Toast.LENGTH_SHORT) {
        errorHandler.handleError(
            message = message
        )
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

    fun resetCreateServerRoleEvent() {
        _createServerRoleEvent.value = null
    }

    fun resetDeleteInvitationEvent() {
        _deleteInvitationEvent.value = null
    }

    fun resetDeleteServerMemberEvent() {
        _deleteServerMemberEvent.value = null
    }

    fun resetPatchServerOwnerEvent() {
        _patchServerOwnerEvent.value = null
    }

    fun resetAssignRoleEvent() {
        _assignRoleEvent.value = null
    }

    fun resetRevokeRoleEvent() {
        _revokeRoleEvent.value = null
    }

    fun resetPatchServerRoleEvent() {
        _patchServerRoleEvent.value = null
    }

    fun resetDeleteRoleEvent() {
        _deleteRoleEvent.value = null
    }

    fun resetUpdateServerPhotoEvent() {
        _updateServerPhotoEvent.value = null
    }

    fun resetPatchServerPropertiesEvent() {
        _patchServerPropertiesEvent.value = null
    }

    fun resetLoadChosenServerRolesEvent() {
        _loadChosenServerRolesEvent.value = null
    }
}