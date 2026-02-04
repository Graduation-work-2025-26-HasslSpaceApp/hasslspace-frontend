package ru.hse.app.androidApp.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.androidApp.ui.components.auth.login.LoginScreenContent
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.loginEvent.collectAsState()

//    LaunchedEffect(event) {
//        when (event) {
//            is SavePhotoEvent.SuccessSave -> {
//                viewModel.showToast("Фотография успешно добавлена")
//            }
//
//            is SavePhotoEvent.Error -> {
//                val message = (event as SavePhotoEvent.Error).message
//                viewModel.showToast(message)
//            }
//
//            null -> {}
//        }
//
//        viewModel.resetSavePhotoEvent()
//
//    }

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
    val context = LocalContext.current

    LoginScreenContent(
        email = data.email,
        onEmailChanged = { /*TODO */ },
        password = data.password,
        onPasswordChanged = { /*TODO */ },
        isDarkTheme = viewModel.isDarkTheme,
        onLoginClick = { /*TODO */ },
        onGoToRegisterClick = { /*TODO */ },
    )
}