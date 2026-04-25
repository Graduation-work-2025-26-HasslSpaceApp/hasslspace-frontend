package ru.hse.app.hasslspace.screen.serversettings

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
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.components.servers.editserver.SettingsServer
import ru.hse.app.hasslspace.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.GetServerRolesEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.PatchServerPropertiesEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.UpdateServerPhotoEvent
import ru.hse.app.hasslspace.ui.entity.model.serversettings.ServerSettingsUiState
import ru.hse.app.hasslspace.ui.navigation.NavigationItem
import ru.hse.app.hasslspace.ui.notification.ToastManager

@Composable
fun ServerSettingsMainScreen(
    navController: NavController,
    serverId: String,
    viewModel: ServerSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val getServerInfoEvent by viewModel.getServerInfoEvent.collectAsState()
    val getServerRolesEvent by viewModel.getServerRolesEvent.collectAsState()
    val patchServerPropertiesEvent by viewModel.patchServerPropertiesEvent.collectAsState()
    val updateServerPhotoEvent by viewModel.updateServerPhotoEvent.collectAsState()

    LaunchedEffect(serverId) {
        viewModel.getServerInfo(serverId)
    }

    LaunchedEffect(patchServerPropertiesEvent) {
        when (patchServerPropertiesEvent) {
            is PatchServerPropertiesEvent.Success -> {
                viewModel.errorHandler("Данные успешно изменены")
                viewModel.getServerInfo(serverId)
                navController.popBackStack()
            }

            is PatchServerPropertiesEvent.Error -> {
                val message =
                    (patchServerPropertiesEvent as PatchServerPropertiesEvent.Error).message
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetPatchServerPropertiesEvent()
    }

    LaunchedEffect(updateServerPhotoEvent) {
        when (updateServerPhotoEvent) {
            is UpdateServerPhotoEvent.Success -> {
                viewModel.errorHandler("Фото успешно изменено")
            }

            is UpdateServerPhotoEvent.Error -> {
                val message = (updateServerPhotoEvent as UpdateServerPhotoEvent.Error).message
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetUpdateServerPhotoEvent()
    }

    LaunchedEffect(getServerInfoEvent) {
        when (getServerInfoEvent) {
            is GetServerInfoEvent.SuccessLoad -> {}

            is GetServerInfoEvent.Error -> {
                val message = (getServerInfoEvent as GetServerInfoEvent.Error).message
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetGetServerInfoEvent()
    }

    LaunchedEffect(getServerRolesEvent) {
        when (getServerRolesEvent) {
            is GetServerRolesEvent.SuccessLoad -> {}

            is GetServerRolesEvent.Error -> {
                val message = (getServerRolesEvent as GetServerRolesEvent.Error).message
                viewModel.errorHandler(message)
            }

            null -> {}
        }
        viewModel.resetGetServerRolesEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ServerSettingsUiState.Loading -> {
                LoadingScreen()
            }

            is ServerSettingsUiState.Error -> {
                ErrorScreen()
            }

            is ServerSettingsUiState.Success -> {
                ServerSettingsMainScreenWithStateContent(
                    uiState = uiState as ServerSettingsUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun ServerSettingsMainScreenWithStateContent(
    uiState: ServerSettingsUiState.Success,
    navController: NavController,
    viewModel: ServerSettingsViewModel
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

    SettingsServer(
        onBackClick = { navController.popBackStack() },
        selectedImageUri = viewModel.selectedImageUri.value,
        onPhotoPickClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        editedServerName = viewModel.serverName.value,
        onEditedServernameChanged = viewModel::onEditedServernameChanged,
        enabledChangeServerName = viewModel.isEnabledChange(viewModel.serverName.value),
        onApplyNewServerName = {
            viewModel.onSaveEditedServername(
                data.id,
                viewModel.serverName.value
            )
        },
        onMembersClick = { navController.navigate(NavigationItem.MembersSettings.route + "/${data.id}") },
        onRolesClick = { navController.navigate(NavigationItem.RolesSettings.route + "/${data.id}") },
        onInvitationsClick = { navController.navigate(NavigationItem.InvitationsSettings.route + "/${data.id}") },
        isDark = viewModel.isDarkTheme
    )
}