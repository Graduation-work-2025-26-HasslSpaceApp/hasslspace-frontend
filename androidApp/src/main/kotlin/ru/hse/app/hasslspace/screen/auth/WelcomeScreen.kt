package ru.hse.app.hasslspace.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.hasslspace.ui.components.auth.welcome.WelcomeScreenContent
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.entity.model.auth.AuthUiState
import ru.hse.app.hasslspace.ui.navigation.AuthNavigationItem

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is AuthUiState.Loading -> {
                LoadingScreen()
            }

            is AuthUiState.Error -> {
                ErrorScreen()
            }

            is AuthUiState.Success -> {
                WelcomeScreenWithStateContent(
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun WelcomeScreenWithStateContent(
    navController: NavController,
    viewModel: AuthViewModel,
) {

    WelcomeScreenContent(
        isDarkTheme = viewModel.isDarkTheme,
        onLoginClick = { navController.navigate(AuthNavigationItem.LoginScreen.route) },
        onRegisterClick = { navController.navigate(AuthNavigationItem.RegisterScreen.route) }
    )
}