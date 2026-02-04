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
import ru.hse.app.androidApp.ui.components.auth.register.RegisterScreenContent
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.registerEvent.collectAsState()

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
    val context = LocalContext.current

    RegisterScreenContent(
        nickname = data.nickname,
        username = data.username,
        email = data.email,
        password = data.password,
        passwordAgain = data.passwordAgain,
        onNicknameChanged = { /*TODO */ },
        onUsernameChanged = { /*TODO */ },
        onEmailChanged = { /*TODO */ },
        onPasswordChanged = { /*TODO */ },
        onPasswordAgainChanged = { /*TODO */ },
        isDarkTheme = viewModel.isDarkTheme,
        onRegisterClick = { /*TODO */ },
        onGoToLoginClick = { /*TODO */ },
    )
}