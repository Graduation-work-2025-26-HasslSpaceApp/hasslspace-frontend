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
import ru.hse.app.androidApp.ui.components.auth.verification.VerificationScreenContent
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState

@Composable
fun VerificationScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sendCodeEvent by viewModel.sendCodeEvent.collectAsState()
    val verifyCodeEvent by viewModel.verifyCodeEvent.collectAsState()

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
                VerificationScreenWithStateContent(
                    uiState = uiState as AuthUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun VerificationScreenWithStateContent(
    uiState: AuthUiState.Success,
    navController: NavController,
    viewModel: AuthViewModel,
) {
    val data = uiState.data
    val context = LocalContext.current

    VerificationScreenContent(
        code = data.code,
        onCodeChange = { _, _ -> /*TODO */ },
        isDarkTheme = viewModel.isDarkTheme,
        onConfirm = { /*TODO */ },
        onResend = { /*TODO */ },
        onBackClick = {/*TODO */ }
    )
}