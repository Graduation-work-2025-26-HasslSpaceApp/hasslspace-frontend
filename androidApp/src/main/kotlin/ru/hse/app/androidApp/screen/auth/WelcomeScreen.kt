package ru.hse.app.androidApp.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import ru.hse.app.androidApp.ui.components.auth.welcome.WelcomeScreenContent
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                WelcomeScreenWithStateContent(
                    uiState = uiState as AuthUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun WelcomeScreenWithStateContent(
    uiState: AuthUiState.Success,
    navController: NavController,
    viewModel: AuthViewModel,
) {

    WelcomeScreenContent(
        isDarkTheme = viewModel.isDarkTheme,
        onLoginClick = { /*TODO*/ },
        onRegisterClick = { /*TODO*/ }
    )
}