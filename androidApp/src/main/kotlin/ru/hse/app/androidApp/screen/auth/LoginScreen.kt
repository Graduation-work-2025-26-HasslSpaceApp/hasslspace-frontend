package ru.hse.app.androidApp.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.androidApp.ui.components.auth.login.LoginScreenContent
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState
import ru.hse.app.androidApp.ui.entity.model.auth.events.CheckEmailVerificationEvent
import ru.hse.app.androidApp.ui.entity.model.auth.events.LoginUserEvent
import ru.hse.app.androidApp.ui.navigation.AuthNavigationItem

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val loginUserEvent by viewModel.loginEvent.collectAsState()
    val checkEmailVerificationEvent by viewModel.checkEmailVerificationEvent.collectAsState()

    LaunchedEffect(loginUserEvent) {
        viewModel.connectCentrifugoClient()
        when (loginUserEvent) {
            is LoginUserEvent.SuccessLogin -> {
                viewModel.checkEmailVerification()
            }

            is LoginUserEvent.Error -> {
                val message = (loginUserEvent as LoginUserEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetLoginEvent()
    }

    LaunchedEffect(checkEmailVerificationEvent) {
        when (checkEmailVerificationEvent) {
            is CheckEmailVerificationEvent.SuccessChecked -> {
                if (!(checkEmailVerificationEvent as CheckEmailVerificationEvent.SuccessChecked).verified) {
                    viewModel.sendCode()
                    navController.navigate(AuthNavigationItem.VerificationScreen.route + "/login")
                }
            }

            is CheckEmailVerificationEvent.Error -> {
                val message =
                    (checkEmailVerificationEvent as CheckEmailVerificationEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetCheckVerifyEmailEvent()
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
                LoginScreenWithStateContent(
                    uiState = uiState as AuthUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun LoginScreenWithStateContent(
    uiState: AuthUiState.Success,
    navController: NavController,
    viewModel: AuthViewModel,
) {
    val data = uiState.data

    LoginScreenContent(
        email = data.email,
        onEmailChanged = viewModel::onEmailChanged,
        password = data.password,
        onPasswordChanged = viewModel::onPasswordChanged,
        isDarkTheme = viewModel.isDarkTheme,
        onLoginClick = viewModel::loginUser,
        onGoToRegisterClick = { navController.navigate(AuthNavigationItem.RegisterScreen.route) },
    )
}