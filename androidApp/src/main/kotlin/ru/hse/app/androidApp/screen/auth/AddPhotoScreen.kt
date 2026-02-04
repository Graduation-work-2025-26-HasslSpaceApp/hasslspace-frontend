package ru.hse.app.androidApp.screen.auth

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
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
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.ui.components.auth.photoloading.AddPhotoScreenContent
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState
import ru.hse.app.androidApp.ui.entity.model.auth.SavePhotoEvent
import ru.hse.app.androidApp.ui.notification.ToastManager

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
                viewModel.showToast("Фотография успешно добавлена")
            }

            is SavePhotoEvent.Error -> {
                val message = (event as SavePhotoEvent.Error).message
                viewModel.showToast(message)
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

    //TODO
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val fileSizeInBytes = CropProfilePhotoService().getImageSize(context, it)
                val maxSizeInBytes = 20 * 1024 * 1024

                if (fileSizeInBytes > maxSizeInBytes) {
                    ToastManager(context).showToast("Файл слишком большой. Максимальный размер 20 МБ.")
                } else {
//                    viewModel.cropProfilePhotoService.startCrop(it, context, cropLauncher)
                }
            }
        }
    )

    AddPhotoScreenContent(
        selectedImageUri = data.selectedImageUri,
        onPickImageClick = { imagePickerLauncher.launch("image/*") },
        onContinueClick = {
            //TODO
//                viewModel.addProfilePhoto()
            //              viewModel.saveVerificationStatusToStorage()
        },
        onSkipClick = {
//                viewModel.saveVerificationStatusToStorage()
        },
        isDarkTheme = viewModel.isDarkTheme
    )
}