package ru.hse.app.androidApp.screen.servers

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
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.servers.myservers.MyServersContent
import ru.hse.app.androidApp.ui.entity.model.servers.ServersUiState
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetUserServersEvent
import ru.hse.app.androidApp.ui.navigation.NavigationItem

@Composable
fun ServersScreen(
    navController: NavController,
    viewModel: ServersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val getUserServersEvent by viewModel.getUserServersEvent.collectAsState()

    LaunchedEffect(getUserServersEvent) {
        when (getUserServersEvent) {
            is GetUserServersEvent.SuccessLoad -> {}

            is GetUserServersEvent.Error -> {
                val message = (getUserServersEvent as GetUserServersEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetGetUserServersEvent()
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
                ServersScreenWithStateContent(
                    uiState = uiState as ServersUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun ServersScreenWithStateContent(
    uiState: ServersUiState.Success,
    navController: NavController,
    viewModel: ServersViewModel,
) {
    val data = uiState.data
    val context = LocalContext.current

    MyServersContent(
        imageLoader = context.imageLoader,
        servers = data.userServers,
        onServerClick = {/*todo*/ },
        searchText = viewModel.searchServersText.value,
        onValueChange = viewModel::onSearchServersValueChange,
        isDarkTheme = viewModel.isDarkTheme,
        onAddClick = { navController.navigate(NavigationItem.CreateServer.route) },
    )
}