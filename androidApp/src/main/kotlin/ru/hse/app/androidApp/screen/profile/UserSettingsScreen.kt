package ru.hse.app.androidApp.screen.profile

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
import ru.hse.app.androidApp.ui.components.common.dialog.RowButtonDialog
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.settings.usersettings.StatusChangeBottomSheet
import ru.hse.app.androidApp.ui.components.settings.usersettings.UserSettingsScreenContent
import ru.hse.app.androidApp.ui.entity.model.auth.events.SavePhotoEvent
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState
import ru.hse.app.androidApp.ui.entity.model.profile.events.SaveUserDescEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.SaveUserNameEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.SaveUserStatusEvent
import ru.hse.app.androidApp.ui.notification.ToastManager


//TODO Обработка ивентов
@Composable
fun UserSettingsScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val savePhotoEvent by viewModel.savePhotoEvent.collectAsState()
    val saveUserNameEvent by viewModel.saveUserNameEvent.collectAsState()
    val saveUserStatusEvent by viewModel.saveUserStatusEvent.collectAsState()
    val saveUserDescEvent by viewModel.saveUserDescEvent.collectAsState()

    LaunchedEffect(savePhotoEvent) {
        when (savePhotoEvent) {
            is SavePhotoEvent.SuccessSave -> {
                viewModel.showToast("Успешно сохранили новое фото")
            }

            is SavePhotoEvent.Error -> {
                val message = (savePhotoEvent as SavePhotoEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetSavePhotoEvent()
    }

    LaunchedEffect(saveUserNameEvent) {
        when (saveUserNameEvent) {
            is SaveUserNameEvent.SuccessSave -> {
                viewModel.showToast("Успешно сохранили новое имя")
            }

            is SaveUserNameEvent.Error -> {
                val message = (saveUserNameEvent as SaveUserNameEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetSaveUserNameEvent()
    }

    LaunchedEffect(saveUserStatusEvent) {
        when (saveUserStatusEvent) {
            is SaveUserStatusEvent.SuccessSave -> {
                viewModel.showToast("Успешно сохранили новый статус")
            }

            is SaveUserStatusEvent.Error -> {
                val message = (saveUserStatusEvent as SaveUserStatusEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetSaveUserStatusEvent()
    }

    LaunchedEffect(saveUserDescEvent) {
        when (saveUserDescEvent) {
            is SaveUserDescEvent.SuccessSave -> {
                viewModel.showToast("Успешно сохранили новое описание")
            }

            is SaveUserDescEvent.Error -> {
                val message = (saveUserDescEvent as SaveUserDescEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetSaveUserDescEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ProfileUiState.Loading -> {
                LoadingScreen()
            }

            is ProfileUiState.Error -> {
                ErrorScreen()
            }

            is ProfileUiState.Success -> {
                UserSettingsWithStateContent(
                    uiState = uiState as ProfileUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun UserSettingsWithStateContent(
    uiState: ProfileUiState.Success,
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val data = uiState.data
    val context = LocalContext.current

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val croppedUri = UCrop.getOutput(result.data!!)
                viewModel.onSelectedImageUri(croppedUri)
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

    UserSettingsScreenContent(
        onBackClick = { navController.popBackStack() },
        isDarkTheme = data.isDarkCheck,
        selectedStatus = data.status,
        onStatusArrowClick = { viewModel.showStatusSheet.value = true },
        onPhotoPickClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        selectedImageUri = data.selectedImageUri,
        editedUsername = data.username,
        onEditedUsernameChanged = viewModel::onUsernameChanged,
        enabledChangeUsername = (!viewModel.isUsernameMatched.value && data.username.isNotEmpty()),
        onApplyNewUsername = viewModel::onApplyNewUsername,
        description = data.description,
        onDescChanged = viewModel::onDescChanged,
        onApplyDescClick = viewModel::onApplyDescClick,
        onExit = { viewModel.showExitSheet.value = true },
    )

    if (viewModel.showStatusSheet.value) {
        StatusChangeBottomSheet(
            options = viewModel.getStatusOptions(),
            selectedOption = data.status,
            onSelectedOptionChanged = viewModel::onSelectedStatusChanged,
            onApply = viewModel::onApplyStatus,
            showSortSheet = viewModel.showStatusSheet,
            onDismiss = viewModel::onDismiss
        )
    }

    if (viewModel.showExitSheet.value) {
        RowButtonDialog(
            showDialog = viewModel.showExitSheet,
            questionText = "Вы действительно хотите выйти из аккаунта?",
            apply = "Выйти",
            dismiss = "Остаться",
            onApplyClick = viewModel::exit,
            onDismissClick = { viewModel.showExitSheet.value = false },
        )
    }
}