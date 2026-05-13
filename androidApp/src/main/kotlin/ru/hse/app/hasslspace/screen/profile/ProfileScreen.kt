package ru.hse.app.hasslspace.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.components.profile.user.ProfileScreenContent
import ru.hse.app.hasslspace.ui.entity.model.TypeUiModel
import ru.hse.app.hasslspace.ui.entity.model.profile.ProfileUiState
import ru.hse.app.hasslspace.ui.entity.model.profile.events.LoadUserDataEvent
import ru.hse.app.hasslspace.ui.navigation.BottomNavigationItem
import ru.hse.app.hasslspace.ui.navigation.NavigationItem

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val loadUserDataEvent by viewModel.loadUserInfoEvent.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserData()
    }

    LaunchedEffect(loadUserDataEvent) {
        when (loadUserDataEvent) {
            is LoadUserDataEvent.SuccessLoad -> {}

            is LoadUserDataEvent.Error -> {
                val message =
                    (loadUserDataEvent as LoadUserDataEvent.Error).message
                val exception = (loadUserDataEvent as LoadUserDataEvent.Error).exception
                viewModel.handleError(message, exception)
            }

            null -> {}
        }
        viewModel.resetLoadUserInfoEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ProfileUiState.Loading -> {
                LoadingScreen()
            }

            is ProfileUiState.Error -> {
                ErrorScreen()
            }

            is ProfileUiState.Success -> {
                ProfileScreenWithStateContent(
                    uiState = uiState as ProfileUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun ProfileScreenWithStateContent(
    uiState: ProfileUiState.Success,
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val data = uiState.data
    val isDark by viewModel.isDark.collectAsState()

    ProfileScreenContent(
        imageLoader = viewModel.imageLoader,
        username = data.name,
        nickname = data.username,
        status = data.status,
        profilePictureUrl = data.profilePictureUrl,
        friendsCount = data.friends.filter { it.type == TypeUiModel.FRIEND }.size,
        onFriendsClick = { navController.navigate(NavigationItem.MyFriends.route) },
        serversCount = data.servers.size,
        onServersClick = { navController.navigate(BottomNavigationItem.Servers.route) },
        onSettingsClick = { navController.navigate(NavigationItem.Settings.route) },
        isDarkTheme = isDark,
    )
}