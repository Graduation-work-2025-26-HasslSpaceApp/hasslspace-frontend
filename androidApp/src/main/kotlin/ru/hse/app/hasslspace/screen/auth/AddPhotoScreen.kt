package ru.hse.app.hasslspace.screen.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.yalantis.ucrop.UCrop
import ru.hse.app.hasslspace.ui.components.auth.photoloading.AddPhotoScreenContent
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.entity.model.auth.AuthUiState
import ru.hse.app.hasslspace.ui.entity.model.auth.events.SavePhotoEvent
import ru.hse.app.hasslspace.ui.notification.ToastManager

@Composable
fun AddPhotoScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.savePhotoEvent.collectAsState()

    LaunchedEffect(event) {
        when (event) {
            is SavePhotoEvent.SuccessSave -> {
                viewModel.saveVerificationStatusToStorage(true)
                viewModel.connectCentrifugoClient()
                viewModel.handleInfo("Фотография успешно добавлена")
            }

            is SavePhotoEvent.Error -> {
                val message = (event as SavePhotoEvent.Error).message
                val exception = (event as SavePhotoEvent.Error).exception
                viewModel.handleError(message, exception)
                viewModel.saveVerificationStatusToStorage(true)
                viewModel.connectCentrifugoClient()
            }

            null -> {}
        }


        viewModel.resetSavePhotoEvent()

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
                AddPhotoScreenWithStateContent(
                    uiState = uiState as AuthUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun AddPhotoScreenWithStateContent(
    uiState: AuthUiState.Success,
    navController: NavController,
    viewModel: AuthViewModel,
) {
    val data = uiState.data
    val context = LocalContext.current

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val croppedUri = UCrop.getOutput(result.data!!)
                viewModel.onPhotoUriChanged(croppedUri)
            }
        }
    )

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                val fileSizeInBytes = viewModel.cropProfilePhotoService.getImageSize(context, it)
                val maxSizeInBytes = 20 * 1024 * 1024

                if (fileSizeInBytes > maxSizeInBytes) {
                    ToastManager(context).showToast("Файл слишком большой. Максимальный размер 20 МБ.")
                } else {
                    viewModel.cropProfilePhotoService.startCrop(it, context, cropLauncher)
                }
            }
        }

    AddPhotoScreenContent(
        selectedImageUri = data.selectedImageUri,
        onPickImageClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        onContinueClick = {
            viewModel.addProfilePhoto()
            viewModel.saveVerificationStatusToStorageFromState()
        },
        onSkipClick = {
            viewModel.saveVerificationStatusToStorageFromState()
        },
        isDarkTheme = viewModel.isDarkTheme
    )
}