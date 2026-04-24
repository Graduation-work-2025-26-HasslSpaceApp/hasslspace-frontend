package ru.hse.app.hasslspace.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.components.settings.mainsettings.SettingsMainScreenContent
import ru.hse.app.hasslspace.ui.entity.model.profile.ProfileUiState
import ru.hse.app.hasslspace.ui.navigation.NavigationItem

@Composable
fun SettingsScreen(
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
                SettingsWithStateContent(
                    uiState = uiState as ProfileUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun SettingsWithStateContent(
    uiState: ProfileUiState.Success,
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val data = uiState.data
    val isDark by viewModel.isDark.collectAsState()

    SettingsMainScreenContent(
        onBackClick = { navController.popBackStack() },
        onUserSettingsClick = { navController.navigate(NavigationItem.UserSettings.route) },
        onSystemSettingsClick = { navController.navigate(NavigationItem.SystemSettings.route) },
        isDarkTheme = isDark
    )

}