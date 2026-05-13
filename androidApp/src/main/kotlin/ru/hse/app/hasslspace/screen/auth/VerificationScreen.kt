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
import ru.hse.app.hasslspace.ui.components.auth.verification.VerificationScreenContent
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.entity.model.auth.AuthUiState
import ru.hse.app.hasslspace.ui.entity.model.auth.events.SendVerificationCodeEvent
import ru.hse.app.hasslspace.ui.entity.model.auth.events.VerifyCodeEvent
import ru.hse.app.hasslspace.ui.entity.model.auth.events.VerifyUserEvent
import ru.hse.app.hasslspace.ui.navigation.AuthNavigationItem

@Composable
fun VerificationScreen(
    navController: NavController,
    type: String,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val verifyUserEvent by viewModel.verifyUserEvent.collectAsState()
    val verifyCodeEvent by viewModel.verifyCodeEvent.collectAsState()
    val sendCodeEvent by viewModel.sendCodeEvent.collectAsState()

    LaunchedEffect(sendCodeEvent) {
        when (sendCodeEvent) {
            is SendVerificationCodeEvent.SuccessSend -> {}

            is SendVerificationCodeEvent.Error -> {
                val message = (sendCodeEvent as SendVerificationCodeEvent.Error).message
                val exception = (sendCodeEvent as SendVerificationCodeEvent.Error).exception
                viewModel.handleError(message, exception)
            }

            null -> {}
        }

        viewModel.resetSendCodeEvent()

    }

    LaunchedEffect(verifyCodeEvent) {
        when (verifyCodeEvent) {
            is VerifyCodeEvent.SuccessVerify -> {}

            is VerifyCodeEvent.Error -> {
                val message = (verifyCodeEvent as VerifyCodeEvent.Error).message
                val exception = (verifyCodeEvent as VerifyCodeEvent.Error).exception
                viewModel.handleError(message, exception)
            }

            null -> {}
        }

        viewModel.resetVerifyCodeEvent()

    }

    LaunchedEffect(verifyUserEvent) {
        when (verifyUserEvent) {
            is VerifyUserEvent.SuccessVerify -> {
                if (type != "login") {
                    navController.navigate(AuthNavigationItem.AddPhotoScreen.route)
                } else {
                    viewModel.saveVerificationStatusToStorage(true)
                    viewModel.connectCentrifugoClient()
                }
            }

            is VerifyUserEvent.Error -> {
                val message = (verifyUserEvent as VerifyUserEvent.Error).message
                val exception = (verifyUserEvent as VerifyUserEvent.Error).exception
                viewModel.handleError(message, exception)
            }

            null -> {}
        }

        viewModel.resetVerifyUserEvent()

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
                VerificationScreenWithStateContent(
                    uiState = uiState as AuthUiState.Success,
                    type = type,
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
    type: String,
    navController: NavController,
    viewModel: AuthViewModel,
) {
    val data = uiState.data

    VerificationScreenContent(
        code = data.code,
        onCodeChange = viewModel::onDigitInCodeChanged,
        isDarkTheme = viewModel.isDarkTheme,
        onConfirm = {
            viewModel.verifyUser(type)
        },
        onResend = {
            viewModel.onWasSentChanged(false)
            viewModel.sendCode()
        },
        onBackClick = { navController.navigate(AuthNavigationItem.RegisterScreen.route) }
    )
}