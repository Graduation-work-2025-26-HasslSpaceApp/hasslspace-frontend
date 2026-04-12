package ru.hse.app.androidApp.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.settings.systemsettings.SystemSettingsContent
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState

@Composable
fun SystemSettingsScreen(
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
                SystemSettingsWithStateContent(
                    uiState = uiState as ProfileUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun SystemSettingsWithStateContent(
    uiState: ProfileUiState.Success,
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val data = uiState.data
    val isDark by viewModel.isDark.collectAsState()

    SystemSettingsContent(
        onBackClick = { navController.popBackStack() },
        onCheckedChange = viewModel::onIsDarkChanged,
        isDarkCheck = isDark,
        isDarkTheme = isDark
    )

}