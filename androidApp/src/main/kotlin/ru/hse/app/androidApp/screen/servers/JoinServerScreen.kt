package ru.hse.app.androidApp.screen.servers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.servers.joinserver.JoinServerContent
import ru.hse.app.androidApp.ui.entity.model.servers.ServersUiState
import ru.hse.app.androidApp.ui.entity.model.servers.events.JoinServerEvent
import ru.hse.app.androidApp.ui.navigation.BottomNavigationItem

@Composable
fun JoinServerScreen(
    navController: NavController,
    viewModel: ServersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val joinServerEvent by viewModel.joinServerEvent.collectAsState()

    LaunchedEffect(joinServerEvent) {
        when (joinServerEvent) {
            is JoinServerEvent.Success -> {
                viewModel.showToast("Успешно присоединились к серверу")
                navController.navigate(BottomNavigationItem.Servers.route)
            }

            is JoinServerEvent.Error -> {
                val message = (joinServerEvent as JoinServerEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetJoinServerEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ServersUiState.Loading -> {
                LoadingScreen()
            }

            is ServersUiState.Error -> {
                ErrorScreen()
            }

            is ServersUiState.Success -> {
                JoinServerScreenWithStateContent(
                    uiState = uiState as ServersUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun JoinServerScreenWithStateContent(
    uiState: ServersUiState.Success,
    navController: NavController,
    viewModel: ServersViewModel,
) {
    JoinServerContent(
        onBackClick = { navController.popBackStack() },
        linkText = viewModel.linkTextServer.value,
        onLinkTextChanged = viewModel::onLinkServerValueChange,
        onButtonClick = { viewModel.joinServer(viewModel.linkTextServer.value) },
    )
}
