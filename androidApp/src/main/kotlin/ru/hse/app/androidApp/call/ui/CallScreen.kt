package ru.hse.app.androidApp.call.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import io.livekit.android.compose.local.RoomScope
import ru.hse.app.androidApp.call.CallViewModel
import ru.hse.app.androidApp.call.state.rememberCallPermissions
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun CallScreen(
    url: String,
    token: String,
    roomName: String,
    onDisconnect: () -> Unit,
    viewModel: CallViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val pinnedTrack by viewModel.pinnedTrack.collectAsState()
    val permissions = rememberCallPermissions()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        if (!permissions.allGranted) {
            permissions.requestCallPermissions()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.errors.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    RoomScope(
        url = url,
        token = token,
        audio = uiState.isMicEnabled && permissions.hasMicPermission,
        video = uiState.isCameraEnabled && permissions.hasCameraPermission,
        connect = true,
        onError = { _, error -> error?.let { viewModel.onError(it) } },
    ) { room ->
        LaunchedEffect(room) {
            viewModel.onRoomConnected()
        }

        val remoteParticipantCount = room.remoteParticipants.size
        LaunchedEffect(remoteParticipantCount) {
            viewModel.onParticipantCountChanged(remoteParticipantCount)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isConnecting) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                )
            } else {
                CallLayout(
                    roomName = roomName,
                    pinnedTrack = pinnedTrack,
                    isMicEnabled = uiState.isMicEnabled,
                    isCameraEnabled = uiState.isCameraEnabled,
                    participantCount = uiState.participantCount,
                    onTrackSelected = { trackRef -> viewModel.pinTrack(trackRef) },
                    onPinInvalidated = { viewModel.clearPin() },
                    onToggleMic = {
                        if (permissions.hasMicPermission) {
                            viewModel.toggleMic(room)
                        } else {
                            permissions.requestMicPermission()
                        }
                    },
                    onToggleCamera = {
                        if (permissions.hasCameraPermission) {
                            viewModel.toggleCamera(room)
                        } else {
                            permissions.requestCameraPermission()
                        }
                    },
                    onFlipCamera = { viewModel.flipCamera(room) },
                    onDisconnect = {
                        room.disconnect()
                        onDisconnect()
                    },
                )
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

// ─── Превью ───────────────────────────────────────────────────────────────────

@Preview(name = "Подключение", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewConnecting() {
    AppTheme(isDark = false) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(name = "Подключение (dark)", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewConnectingDark() {
    AppTheme(isDark = true) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(name = "Snackbar — ошибка", showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun PreviewSnackbarError() {
    AppTheme(isDark = false) {
        val snackbarHostState = remember { SnackbarHostState() }
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar("Не удалось подключиться к серверу")
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize(),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Экран звонка",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}