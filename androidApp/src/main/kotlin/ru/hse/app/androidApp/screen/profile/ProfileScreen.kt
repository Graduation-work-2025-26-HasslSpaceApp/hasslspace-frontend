package ru.hse.app.androidApp.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.profile.user.ProfileScreenContent
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState
import ru.hse.app.androidApp.ui.navigation.BottomNavigationItem
import ru.hse.app.androidApp.ui.navigation.NavigationItem


// TODO подумать про обновление данных экрана при переходах и тп
@Composable
fun ProfileScreen(
    bottomNavHostController: NavHostController,
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                    bottomNavHostController = bottomNavHostController,
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
    bottomNavHostController: NavHostController,
    viewModel: ProfileViewModel,
) {
    val data = uiState.data

    ProfileScreenContent(
        imageLoader = viewModel.imageLoader,
        username = data.username,
        nickname = data.nickname,
        status = data.status,
        profilePictureUrl = data.profilePictureUrl,
        friendsCount = data.friends.size,
        onFriendsClick = { navController.navigate(NavigationItem.MyFriends.route) },
        serversCount = data.servers.size,
        onServersClick = { bottomNavHostController.navigate(BottomNavigationItem.Servers.route) },
        onSettingsClick = { navController.navigate(NavigationItem.Settings.route) },
        isDarkTheme = data.isDarkCheck,
    )
}