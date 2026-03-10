package ru.hse.app.androidApp.screen.serversettings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import ru.hse.app.androidApp.ui.components.servers.invitations.ServerInvitationsContent
import ru.hse.app.androidApp.ui.components.servers.roles.ColorPickerBlock
import ru.hse.app.androidApp.ui.components.servers.roles.EditRoleContent
import ru.hse.app.androidApp.ui.components.servers.roles.NewRoleContent
import ru.hse.app.androidApp.ui.components.servers.roles.ServerRolesContent
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateServerRoleEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInvitationsEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.androidApp.ui.entity.model.serversettings.ServerSettingsUiState
import ru.hse.app.androidApp.ui.entity.model.toRoleMiniCount
import ru.hse.app.androidApp.ui.navigation.NavigationItem

@Composable
fun InvitationsScreen(
    navController: NavController,
    serverId: String,
    viewModel: ServerSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val getServerInfoEvent by viewModel.getServerInfoEvent.collectAsState()
    val getServerInvitationsEvent by viewModel.getServerInvitationsEvent.collectAsState()

    LaunchedEffect(serverId) {
        viewModel.getServerInfo(serverId)
        viewModel.getServerInvitations(serverId)
    }
    LaunchedEffect(getServerInvitationsEvent) {
        when (getServerInvitationsEvent) {
            is GetServerInvitationsEvent.SuccessLoad -> {}

            is GetServerInvitationsEvent.Error -> {
                val message = (getServerInvitationsEvent as GetServerInvitationsEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetGetServerInvitationsEvent()
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
                InvitationsScreenWithStateContent(
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
fun InvitationsScreenWithStateContent(
    uiState: ServerSettingsUiState.Success,
    navController: NavController,
    serverId: String,
    viewModel: ServerSettingsViewModel
) {
    val data = uiState.data
    val context = LocalContext.current

    ServerInvitationsContent(
        onBackClick = { navController.popBackStack() },
        invitations = data.invitations,
        onInvitationClick = {
            val clipboard =
                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", it.link)
            clipboard.setPrimaryClip(clip)

            viewModel.showToast("Ссылка скопирована в буфер обмена")
        },
        onCancelClick = {/*todo*/},
    )
}