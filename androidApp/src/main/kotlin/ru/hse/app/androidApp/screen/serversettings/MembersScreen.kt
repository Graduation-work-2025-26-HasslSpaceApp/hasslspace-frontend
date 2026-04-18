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
import ru.hse.app.androidApp.ui.components.common.dialog.RowButtonDialog
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.servers.members.EditMemberContent
import ru.hse.app.androidApp.ui.components.servers.members.RolesChoosingDialog
import ru.hse.app.androidApp.ui.components.servers.members.ServerMembersContent
import ru.hse.app.androidApp.ui.entity.model.servers.events.AssignRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerMemberEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.LoadChosenServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.PatchServerOwnerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.RevokeRoleEvent
import ru.hse.app.androidApp.ui.entity.model.serversettings.ServerSettingsUiState
import ru.hse.app.androidApp.ui.navigation.NavigationItem

@Composable
fun MembersScreen(
    navController: NavController,
    serverId: String,
    viewModel: ServerSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val getServerInfoEvent by viewModel.getServerInfoEvent.collectAsState()
    val getServerRolesEvent by viewModel.getServerRolesEvent.collectAsState()
    val loadChosenServerRolesEvent by viewModel.loadChosenServerRolesEvent.collectAsState()
    val deleteServerMemberEvent by viewModel.deleteServerMemberEvent.collectAsState()
    val patchServerOwnerEvent by viewModel.patchServerOwnerEvent.collectAsState()
    val assignRoleEvent by viewModel.assignRoleEvent.collectAsState()
    val revokeRoleEvent by viewModel.revokeRoleEvent.collectAsState()


    LaunchedEffect(serverId) {
        viewModel.getServerInfo(serverId)
    }

    LaunchedEffect(assignRoleEvent) {
        when (assignRoleEvent) {
            is AssignRoleEvent.Success -> {
            }

            is AssignRoleEvent.Error -> {
                val message = (assignRoleEvent as AssignRoleEvent.Error).message
                viewModel.errorHandler(message)
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
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetRevokeRoleEvent()
    }

    LaunchedEffect(patchServerOwnerEvent) {
        when (patchServerOwnerEvent) {
            is PatchServerOwnerEvent.SuccessPatch -> {
                viewModel.getServerInfo(serverId)
                navController.navigate(NavigationItem.MainServerScreen.route + "/${serverId}") {
                    popUpTo(NavigationItem.MainServerScreen.route + "/${serverId}") {
                        inclusive = true
                    }
                }
                viewModel.errorHandler("Успешно передали права участнику")
            }

            is PatchServerOwnerEvent.Error -> {
                val message = (patchServerOwnerEvent as PatchServerOwnerEvent.Error).message
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetPatchServerOwnerEvent()
    }

    LaunchedEffect(deleteServerMemberEvent) {
        when (deleteServerMemberEvent) {
            is DeleteServerMemberEvent.SuccessDelete -> {
                viewModel.getServerInfo(serverId)
                navController.navigate(NavigationItem.MainServerScreen.route + "/${serverId}") {
                    popUpTo(NavigationItem.MainServerScreen.route + "/${serverId}") {
                        inclusive = true
                    }
                }
                viewModel.errorHandler("Успешно удалили участника")
            }

            is DeleteServerMemberEvent.Error -> {
                val message = (deleteServerMemberEvent as DeleteServerMemberEvent.Error).message
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetDeleteServerMemberEvent()
    }

    LaunchedEffect(getServerRolesEvent) {
        when (getServerRolesEvent) {
            is GetServerRolesEvent.SuccessLoad -> {}

            is GetServerRolesEvent.Error -> {
                val message = (getServerRolesEvent as GetServerRolesEvent.Error).message
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetGetServerRolesEvent()
    }

    LaunchedEffect(loadChosenServerRolesEvent) {
        when (loadChosenServerRolesEvent) {
            is LoadChosenServerRolesEvent.SuccessLoad -> {
                viewModel.showChooseRoles.value = true
            }

            is LoadChosenServerRolesEvent.Error -> {
                val message =
                    (loadChosenServerRolesEvent as LoadChosenServerRolesEvent.Error).message
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetLoadChosenServerRolesEvent()
    }

    LaunchedEffect(getServerInfoEvent) {
        when (getServerInfoEvent) {
            is GetServerInfoEvent.SuccessLoad -> {}

            is GetServerInfoEvent.Error -> {
                val message = (getServerInfoEvent as GetServerInfoEvent.Error).message
                viewModel.errorHandler(message)
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
                MembersScreenWithStateContent(
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
fun MembersScreenWithStateContent(
    uiState: ServerSettingsUiState.Success,
    navController: NavController,
    serverId: String,
    viewModel: ServerSettingsViewModel
) {
    val data = uiState.data
    val context = LocalContext.current

    ServerMembersContent(
        imageLoader = viewModel.imageLoader,
        friends = viewModel.searchedMembers,
        onBackClick = { navController.popBackStack() },
        onFriendClick = {
            viewModel.chosenMember.value = it
            viewModel.showEditMember.value = true
        },
        searchText = viewModel.searchMembersText.value,
        onValueChange = viewModel::onSearchMembersText,
        isDarkTheme = viewModel.isDarkTheme
    )

    if (viewModel.showEditMember.value && viewModel.chosenMember.value != null) {
        EditMemberContent(
            imageLoader = viewModel.imageLoader,
            member = viewModel.chosenMember.value!!,
            onBackClick = {
                viewModel.showEditMember.value = false
                viewModel.chosenMember.value = null
            },
            isDarkTheme = viewModel.isDarkTheme,
            onEditRoles = {
                viewModel.loadChosenRoles(serverId)
            },
            onDeleteMember = { viewModel.showDeleteMemberDialog.value = true },
            onTransferRightsToMember = { viewModel.showTransferRights.value = true }
        )
    }

    if (viewModel.showChooseRoles.value) {
        RolesChoosingDialog(
            showDialog = viewModel.showChooseRoles,
            roles = data.chosenRoles,
            onToggleRole = { viewModel.onToggleRole(serverId, it) },
            onApplyClick = {
                viewModel.showChooseRoles.value = false
                viewModel.clearChosenRoles()
                viewModel.getServerInfo(serverId)
            },
            onDismissClick = {
                viewModel.showChooseRoles.value = false
                viewModel.clearChosenRoles()
                viewModel.getServerInfo(serverId)
            }
        )
    }

    if (viewModel.showDeleteMemberDialog.value) {
        RowButtonDialog(
            showDialog = viewModel.showDeleteMemberDialog,
            questionText = "Вы действительно хотите удалить участника?",
            apply = "Удалить",
            dismiss = "Оставить",
            onApplyClick = {
                if (viewModel.chosenMember.value != null) {
                    viewModel.deleteMember(serverId, viewModel.chosenMember.value!!)
                }
                viewModel.showDeleteMemberDialog.value = false
            },
            onDismissClick = { viewModel.showDeleteMemberDialog.value = false }
        )
    }

    if (viewModel.showTransferRights.value) {
        RowButtonDialog(
            showDialog = viewModel.showTransferRights,
            questionText = "Вы действительно хотите передать права на сервер?",
            apply = "Передать",
            dismiss = "Оставить",
            onApplyClick = {
                if (viewModel.chosenMember.value != null) {
                    viewModel.transferRightsToMember(serverId, viewModel.chosenMember.value!!)
                }
                viewModel.showTransferRights.value = false
            },
            onDismissClick = { viewModel.showTransferRights.value = false }
        )
    }

}
