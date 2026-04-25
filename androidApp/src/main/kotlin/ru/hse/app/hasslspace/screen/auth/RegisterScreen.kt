package ru.hse.app.hasslspace.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.hasslspace.ui.components.auth.register.RegisterScreenContent
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.entity.model.auth.AuthUiState
import ru.hse.app.hasslspace.ui.entity.model.auth.events.RegisterUserEvent
import ru.hse.app.hasslspace.ui.navigation.AuthNavigationItem

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.registerEvent.collectAsState()

    LaunchedEffect(event) {
        when (event) {
            is RegisterUserEvent.SuccessRegister -> {
                navController.navigate(AuthNavigationItem.VerificationScreen.route + "/register")
            }

            is RegisterUserEvent.Error -> {
                val message = (event as RegisterUserEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetRegisterEvent()

    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is AuthUiState.Loading -> {
                LoadingScreen()
            }

            is AuthUiState.Error -> {
                ErrorScreen()
            }

            is AuthUiState.Success -> {
                RegisterScreenWithStateContent(
                    uiState = uiState as AuthUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun RegisterScreenWithStateContent(
    uiState: AuthUiState.Success,
    navController: NavController,
    viewModel: AuthViewModel,
) {
    val data = uiState.data

    RegisterScreenContent(
        nickname = data.nickname,
        username = data.username,
        email = data.email,
        password = data.password,
        passwordAgain = data.passwordAgain,
        onNicknameChanged = viewModel::onNicknameChanged,
        onUsernameChanged = viewModel::onUsernameChanged,
        onEmailChanged = viewModel::onEmailChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onPasswordAgainChanged = viewModel::onPasswordAgainChanged,
        isDarkTheme = viewModel.isDarkTheme,
        onRegisterClick = viewModel::registerUser,
        onGoToLoginClick = { navController.navigate(AuthNavigationItem.LoginScreen.route) },
    )
}