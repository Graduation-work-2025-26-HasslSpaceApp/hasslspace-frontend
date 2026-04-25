package ru.hse.app.hasslspace.screen.servers

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
import ru.hse.app.hasslspace.ui.components.servers.createserver.CreateServerContent
import ru.hse.app.hasslspace.ui.entity.model.servers.ServersUiState
import ru.hse.app.hasslspace.ui.entity.model.servers.events.CreateServerEvent
import ru.hse.app.hasslspace.ui.navigation.BottomNavigationItem
import ru.hse.app.hasslspace.ui.navigation.NavigationItem
import ru.hse.app.hasslspace.ui.notification.ToastManager

@Composable
fun CreateServerScreen(
    navController: NavController,
    viewModel: ServersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val createServerEvent by viewModel.createServerEvent.collectAsState()

    LaunchedEffect(createServerEvent) {
        when (createServerEvent) {
            is CreateServerEvent.SuccessCreate -> {
                viewModel.handleError("Успешно создали сервер")
                navController.navigate(BottomNavigationItem.Servers.route)
            }

            is CreateServerEvent.Error -> {
                val message = (createServerEvent as CreateServerEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetCreateServerEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ServersUiState.Loading -> {
                LoadingScreen()
            }

            is ServersUiState.Error -> {
                ErrorScreen()
            }

            is ServersUiState.Success -> {
                CreateServerScreenWithStateContent(
                    uiState = uiState as ServersUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun CreateServerScreenWithStateContent(
    uiState: ServersUiState.Success,
    navController: NavController,
    viewModel: ServersViewModel,
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

    CreateServerContent(
        onBackClick = { navController.popBackStack() },
        selectedImageUri = viewModel.selectedImageUri.value,
        onPickImageClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        serverName = viewModel.selectedServerName.value,
        onServerNameChanged = viewModel::onSelectedServerNameChanged,
        onCreateServerClick = {
            viewModel.createServer(
                viewModel.selectedServerName.value,
                viewModel.selectedImageUri.value
            )
        },
        onJoinClick = { navController.navigate(NavigationItem.JoinServer.route) }
    )
}
