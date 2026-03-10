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
import ru.hse.app.androidApp.domain.usecase.servers.CreateServerRoleUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInfoUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerInvitationsUseCase
import ru.hse.app.androidApp.domain.usecase.servers.GetServerRolesUseCase
import ru.hse.app.androidApp.domain.usecase.servers.SearchMembersUseCase
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCountUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateServerRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInvitationsEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerUserRolesEvent
import ru.hse.app.androidApp.ui.entity.model.serversettings.RoleInfoUiModel
import ru.hse.app.androidApp.ui.entity.model.serversettings.ServerSettingsUiModel
import ru.hse.app.androidApp.ui.entity.model.serversettings.ServerSettingsUiState
import ru.hse.app.androidApp.ui.entity.model.serversettings.toRoleInfoUiModel
import ru.hse.app.androidApp.ui.entity.model.serversettings.toServerSettingsUiModel
import ru.hse.app.androidApp.ui.entity.model.toRoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.toRoleMiniIfChosen
import ru.hse.app.androidApp.ui.entity.model.toUi
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class ServerSettingsViewModel @Inject constructor(
    private val createServerRoleUseCase: CreateServerRoleUseCase,

    private val getServerInfoUseCase: GetServerInfoUseCase,
    private val getServerInvitationsUseCase: GetServerInvitationsUseCase,
    private val getServerRolesUseCase: GetServerRolesUseCase,

    private val searchMembersUseCase: SearchMembersUseCase,

    private val dataManager: DataManager,
    private val photoConverterService: PhotoConverterService,
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

    private val _getServerUserRolesEvent = MutableStateFlow<GetServerUserRolesEvent?>(null)
    val getServerUserRolesEvent: StateFlow<GetServerUserRolesEvent?> = _getServerUserRolesEvent

    private val _createServerRoleEvent = MutableStateFlow<CreateServerRoleEvent?>(null)
    val createServerRoleEvent: StateFlow<CreateServerRoleEvent?> = _createServerRoleEvent

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

    fun onToggleEditRoleMember(tapped: FriendCheckboxUiModel) {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState is ServerSettingsUiState.Success) {

                try {
                    if (!tapped.isChosen) {
                        // todo выдать роль
                        // val result = someRepository.assignRole(member.id, tapped.id)
                    } else {
                        // todo забрать роль
                        // val result = someRepository.removeRole(member.id, tapped.id)
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

    fun onToggleNewRoleMember(tapped: FriendCheckboxUiModel) {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState is ServerSettingsUiState.Success) {

                try {
                    if (!tapped.isChosen) {
                        // todo выдать роль
                        // val result = someRepository.assignRole(member.id, tapped.id)
                    } else {
                        // todo забрать роль
                        // val result = someRepository.removeRole(member.id, tapped.id)
                    }

                    val updatedNewRoleMembers = currentState.data.newRole.members.map { member ->
                        if (member.id == tapped.id) {
                            member.copy(isChosen = !member.isChosen)
                        } else {
                            member
                        }
                    }

                    val updatedData = currentState.data.copy(
                        newRole = currentState.data.newRole.copy(
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
        originalEditRoleTitle.value = ""

        // todo сохранить изменения, очистить выбранную роль, перейти просто к ролям
    }

    fun deleteRole(serverId: String, roleId: String) {
        // todo
    }

    fun createServerRole(
        serverId: String,
        newRole: ServerSettingsUiModel.NewRoleUiModel
    ) {
        if (newRole.name.isEmpty()) {
            showToast("Название роли не может быть пустым")
            return
        }
        val colorInt = if (newRole.color != Color.Transparent) newRole.color else Color.LightGray
        viewModelScope.launch {
            val data = CreateRole(
                name = newRole.name,
                color = "#${Integer.toHexString(colorInt.toArgb())}",
                members = newRole.members.filter { it.isChosen }.map { it.id }
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
                                members = currentState.data.members.map { member ->
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

    fun getServerInfo(serverId: String) {
        viewModelScope.launch {
            val result = getServerInfoUseCase(serverId)

            _getServerInfoEvent.value = result.fold(
                onSuccess = { serverInfo ->
                    val serverUi = serverInfo.toServerSettingsUiModel()
                    _uiState.value = ServerSettingsUiState.Success(
                        data = serverUi
                    )
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

            _getServerRolesEvent.value = result.fold(
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

                    GetServerRolesEvent.SuccessLoad
                },
                onFailure = { error ->
                    GetServerRolesEvent.Error("Ошибка при получении ролей сервера. ${error.message}")
                }
            )
        }
    }

    fun onToggleRole(tapped: RoleMiniCheckboxUiModel) {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState is ServerSettingsUiState.Success) {
                val member = chosenMember.value ?: return@launch

                try {
                    if (!tapped.isChosen) {
                        // todo выдать роль
                        // val result = someRepository.assignRole(member.id, tapped.id)
                    } else {
                        // todo забрать роль
                        // val result = someRepository.removeRole(member.id, tapped.id)
                    }

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

    fun transferRightsToMember(member: ServerMemberUiModel?) {
        //todo
    }

    fun deleteMember(member: ServerMemberUiModel?) {
        //todo
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

    fun onSaveEditedServername(value: String) {
        //todo
    }

    fun saveServerPhoto(uri: Uri?) {
        //todo
    }

    fun onSelectedImageUri(uri: Uri?) {
        selectedImageUri.value = uri
        saveServerPhoto(uri)
    }

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toastManager.showToast(
            message = message,
            duration = duration
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

    fun resetGetServerUserRolesEvent() {
        _getServerUserRolesEvent.value = null
    }

    fun resetCreateServerRoleEvent() {
        _createServerRoleEvent.value = null
    }

}