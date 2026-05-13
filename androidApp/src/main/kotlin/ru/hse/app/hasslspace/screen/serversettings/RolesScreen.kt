package ru.hse.app.hasslspace.screen.serversettings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.hasslspace.ui.components.common.dialog.RowButtonDialog
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.components.servers.roles.ColorPickerBlock
import ru.hse.app.hasslspace.ui.components.servers.roles.EditRoleContent
import ru.hse.app.hasslspace.ui.components.servers.roles.NewRoleContent
import ru.hse.app.hasslspace.ui.components.servers.roles.ServerRolesContent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.AssignRoleEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.CreateServerRoleEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.DeleteRoleEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.PatchServerRoleEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.RevokeRoleEvent
import ru.hse.app.hasslspace.ui.entity.model.serversettings.ServerSettingsUiState
import ru.hse.app.hasslspace.ui.entity.model.toRoleMiniCount

@Composable
fun RolesScreen(
    navController: NavController,
    serverId: String,
    viewModel: ServerSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val getServerInfoEvent by viewModel.getServerInfoEvent.collectAsState()
    val getServerRolesEvent by viewModel.getServerRolesEvent.collectAsState()
    val createServerRoleEvent by viewModel.createServerRoleEvent.collectAsState()
    val assignRoleEvent by viewModel.assignRoleEvent.collectAsState()
    val revokeRoleEvent by viewModel.revokeRoleEvent.collectAsState()
    val patchServerRoleEvent by viewModel.patchServerRoleEvent.collectAsState()
    val deleteRoleEvent by viewModel.deleteRoleEvent.collectAsState()

    LaunchedEffect(serverId) {
        viewModel.getServerInfo(serverId)
    }

    LaunchedEffect(deleteRoleEvent) {
        viewModel.showDeleteRoleDialog.value = false
        when (deleteRoleEvent) {
            is DeleteRoleEvent.SuccessDelete -> {
                viewModel.getServerInfo(serverId)
                viewModel.getServerRoles(serverId)
                viewModel.showEditRole.value = false
                viewModel.handleInfo("Роль удалена")
            }

            is DeleteRoleEvent.Error -> {
                val message = (deleteRoleEvent as DeleteRoleEvent.Error).message
                val exception = (deleteRoleEvent as DeleteRoleEvent.Error).exception
                viewModel.errorHandler(message, exception)
            }

            null -> {}
        }
        viewModel.resetDeleteRoleEvent()
    }

    LaunchedEffect(patchServerRoleEvent) {
        when (patchServerRoleEvent) {
            is PatchServerRoleEvent.Success -> {
                viewModel.getServerInfo(serverId)
                viewModel.showEditRole.value = false
            }

            is PatchServerRoleEvent.Error -> {
                val message = (patchServerRoleEvent as PatchServerRoleEvent.Error).message
                val exception = (patchServerRoleEvent as PatchServerRoleEvent.Error).exception
                viewModel.errorHandler(message, exception)
            }

            null -> {}
        }
        viewModel.resetPatchServerRoleEvent()
    }

    LaunchedEffect(assignRoleEvent) {
        when (assignRoleEvent) {
            is AssignRoleEvent.Success -> {
            }

            is AssignRoleEvent.Error -> {
                val message = (assignRoleEvent as AssignRoleEvent.Error).message
                val exception = (assignRoleEvent as AssignRoleEvent.Error).exception
                viewModel.errorHandler(message, exception)
            }

            null -> {}
        }
        viewModel.resetAssignRoleEvent()
    }

    LaunchedEffect(revokeRoleEvent) {
        when (revokeRoleEvent) {
            is RevokeRoleEvent.Success -> {
            }

            is RevokeRoleEvent.Error -> {
                val message = (revokeRoleEvent as RevokeRoleEvent.Error).message
                val exception = (revokeRoleEvent as RevokeRoleEvent.Error).exception
                viewModel.errorHandler(message, exception)
            }

            null -> {}
        }
        viewModel.resetRevokeRoleEvent()
    }

    LaunchedEffect(createServerRoleEvent) {
        when (createServerRoleEvent) {
            is CreateServerRoleEvent.SuccessCreate -> {
                viewModel.getServerInfo(serverId)
                viewModel.getServerRoles(serverId)
                viewModel.showNewRoleScreen.value = false
                viewModel.handleInfo("Успешно создали роль")
            }

            is CreateServerRoleEvent.Error -> {
                val message = (createServerRoleEvent as CreateServerRoleEvent.Error).message
                val exception = (createServerRoleEvent as CreateServerRoleEvent.Error).exception
                viewModel.errorHandler(message, exception)
            }

            null -> {}
        }
        viewModel.resetCreateServerRoleEvent()
    }
    LaunchedEffect(getServerRolesEvent) {
        when (getServerRolesEvent) {
            is GetServerRolesEvent.SuccessLoad -> {}

            is GetServerRolesEvent.Error -> {
                val message = (getServerRolesEvent as GetServerRolesEvent.Error).message
                val exception = (getServerRolesEvent as GetServerRolesEvent.Error).exception
                viewModel.errorHandler(message, exception)
            }

            null -> {}
        }
        viewModel.resetGetServerRolesEvent()
    }

    LaunchedEffect(getServerInfoEvent) {
        when (getServerInfoEvent) {
            is GetServerInfoEvent.SuccessLoad -> {}

            is GetServerInfoEvent.Error -> {
                val message = (getServerInfoEvent as GetServerInfoEvent.Error).message
                val exception = (getServerInfoEvent as GetServerInfoEvent.Error).exception
                viewModel.errorHandler(message, exception)
            }

            null -> {}
        }
        viewModel.resetGetServerInfoEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ServerSettingsUiState.Loading -> {
                LoadingScreen()
            }

            is ServerSettingsUiState.Error -> {
                ErrorScreen()
            }

            is ServerSettingsUiState.Success -> {
                RolesScreenWithStateContent(
                    uiState = uiState as ServerSettingsUiState.Success,
                    navController = navController,
                    serverId = serverId,
                    viewModel = viewModel,
                )
            }
        }
    }

}

@Composable
fun RolesScreenWithStateContent(
    uiState: ServerSettingsUiState.Success,
    navController: NavController,
    serverId: String,
    viewModel: ServerSettingsViewModel
) {
    val data = uiState.data

    ServerRolesContent(
        roles = data.roles.map { it.toRoleMiniCount() },
        onBackClick = { navController.popBackStack() },
        onAddClick = {
            viewModel.showNewRoleScreen.value = true
        },
        onRoleClick = {
            viewModel.loadEditRole(it)
            viewModel.showEditRole.value = true
        },
    )

    if (viewModel.showNewRoleScreen.value) {
        NewRoleContent(
            onBackClick = {
                viewModel.showNewRoleScreen.value = false
            },
            roleName = data.newRole.name,
            onRoleNameChanged = viewModel::onNewRoleNameChanged,
            onSaveClick = { viewModel.createServerRole(data.id, data.newRole) },
            selectedColor = data.newRole.color,
            onColorPickClick = { viewModel.showColorPicker.value = true },
        )
    }

    if (viewModel.showColorPicker.value) {
        ColorPickerBlock(
            onSaveClick = {
                viewModel.onNewRoleColorChanged(it)
                viewModel.showColorPicker.value = false
            },
            showDialog = viewModel.showColorPicker,
        )
    }

    if (viewModel.showColorPickerEdit.value) {
        ColorPickerBlock(
            initColor = data.editedRole.color,
            onSaveClick = {
                viewModel.onEditRoleColorChanged(it)
                viewModel.showColorPickerEdit.value = false
            },
            showDialog = viewModel.showColorPickerEdit,
        )
    }

    if (viewModel.showEditRole.value) {
        EditRoleContent(
            imageLoader = viewModel.imageLoader,
            friends = data.editedRole.members,
            onBackClick = { viewModel.showEditRole.value = false },
            roleName = data.editedRole.name,
            onSaveClick = { viewModel.saveEditedRole(serverId, data.editedRole.id) },
            onToggle = { viewModel.onToggleEditRoleMember(serverId, data.editedRole.id, it) },
            selectedColor = data.editedRole.color,
            onColorPickClick = { viewModel.showColorPickerEdit.value = true },
            isDarkTheme = viewModel.isDarkTheme,
            originalTitle = viewModel.originalEditRoleTitle.value,
            onRoleTextChanged = viewModel::onEditRoleNameChanged,
            onDeleteRoleClick = { viewModel.showDeleteRoleDialog.value = true },
        )
    }

    if (viewModel.showDeleteRoleDialog.value) {
        RowButtonDialog(
            showDialog = viewModel.showDeleteRoleDialog,
            questionText = "Вы действительно хотите удалить роль?",
            apply = "Удалить",
            dismiss = "Оставить",
            onApplyClick = {
                viewModel.deleteRole(
                    serverId,
                    data.editedRole.id,
                    data.editedRole.name
                )
            },
            onDismissClick = { viewModel.showDeleteRoleDialog.value = false }
        )
    }
}