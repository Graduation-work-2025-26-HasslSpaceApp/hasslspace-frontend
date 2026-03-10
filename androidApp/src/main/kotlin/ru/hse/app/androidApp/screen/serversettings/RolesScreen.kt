package ru.hse.app.androidApp.screen.serversettings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.imageLoader
import ru.hse.app.androidApp.ui.components.common.dialog.RowButtonDialog
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.servers.roles.ColorPickerBlock
import ru.hse.app.androidApp.ui.components.servers.roles.EditRoleContent
import ru.hse.app.androidApp.ui.components.servers.roles.NewRoleContent
import ru.hse.app.androidApp.ui.components.servers.roles.ServerRolesContent
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateServerRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.serversettings.ServerSettingsUiState
import ru.hse.app.androidApp.ui.entity.model.toRoleMiniCount
import ru.hse.app.androidApp.ui.navigation.NavigationItem

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

    LaunchedEffect(serverId) {
        viewModel.getServerInfo(serverId)
        viewModel.getServerRoles(serverId)
    }
    LaunchedEffect(createServerRoleEvent) {
        when (createServerRoleEvent) {
            is CreateServerRoleEvent.SuccessCreate -> {
                viewModel.getServerInfo(serverId)
                viewModel.getServerRoles(serverId)
                navController.navigate((NavigationItem.RolesSettings.route + "/${serverId}"))
                viewModel.showToast("Успешно создали роль")
            }

            is CreateServerRoleEvent.Error -> {
                val message = (createServerRoleEvent as CreateServerRoleEvent.Error).message
                viewModel.showToast(message)
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
                viewModel.showToast(message)
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
                viewModel.showToast(message)
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
    val context = LocalContext.current

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
            imageLoader = context.imageLoader,
            friends = data.newRole.members,
            onBackClick = {
                viewModel.showNewRoleScreen.value = false
            },
            roleName = data.newRole.name,
            onRoleNameChanged = viewModel::onNewRoleNameChanged,
            onSaveClick = { viewModel.createServerRole(data.id, data.newRole) },
            onToggle = viewModel::onToggleNewRoleMember,
            selectedColor = data.newRole.color,
            onColorPickClick = { viewModel.showColorPicker.value = true },
            isDarkTheme = viewModel.isDarkTheme
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
            onSaveClick = {
                viewModel.onEditRoleColorChanged(it)
                viewModel.showColorPickerEdit.value = false
            },
            showDialog = viewModel.showColorPickerEdit,
        )
    }

    if (viewModel.showEditRole.value) {
        EditRoleContent(
            imageLoader = context.imageLoader,
            friends = data.editedRole.members,
            onBackClick = { viewModel.showEditRole.value = false },
            roleName = data.editedRole.name,
            onSaveClick = { viewModel.saveEditedRole(serverId, data.editedRole.id) },
            onToggle = viewModel::onToggleEditRoleMember,
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
            onApplyClick = { viewModel.deleteRole(serverId, data.editedRole.id) },
            onDismissClick = { viewModel.showDeleteRoleDialog.value = false }
        )
    }


}