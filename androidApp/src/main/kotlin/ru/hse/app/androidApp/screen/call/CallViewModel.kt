package ru.hse.app.androidApp.screen.call

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.livekit.android.compose.types.TrackReference
import io.livekit.android.room.Room
import io.livekit.android.room.track.LocalVideoTrack
import io.livekit.android.room.track.Track
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

// ─── ViewModel ────────────────────────────────────────────────────────────────
@HiltViewModel
class CallViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val toastManager: ToastManager,
) : ViewModel() {

    val isDark = dataManager.isDark.value

    // UI state — подключение, mic/camera, кол-во участников
    private val _uiState = MutableStateFlow(CallUiState())
    val uiState: StateFlow<CallUiState> = _uiState.asStateFlow()

    // Закреплённый трек (null = авто-выбор)
    private val _pinnedTrack = MutableStateFlow<TrackReference?>(null)
    val pinnedTrack: StateFlow<TrackReference?> = _pinnedTrack.asStateFlow()

    // Одиночные ошибки для Snackbar (SharedFlow — не хранит историю)
    private val _errors = MutableSharedFlow<String>()
    val errors: SharedFlow<String> = _errors.asSharedFlow()

    // ─── Room events ──────────────────────────────────────────────────────────

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toastManager.showToast(
            message = message,
            duration = duration
        )
    }

    fun onRoomConnected() {
        _uiState.value = _uiState.value.copy(isConnecting = false)
    }

    fun onParticipantCountChanged(count: Int) {
        _uiState.value = _uiState.value.copy(participantCount = count)
    }

    fun onError(error: Exception) {
        viewModelScope.launch {
            _errors.emit(error.message ?: "Неизвестная ошибка")
        }
    }

    // ─── Controls ─────────────────────────────────────────────────────────────

    fun updateCameraEnabled(value: Boolean) {
        _uiState.value = _uiState.value.copy(isCameraEnabled = value)
    }

    fun toggleMic(room: Room) {
        viewModelScope.launch {
            val newValue = !_uiState.value.isMicEnabled
            room.localParticipant.setMicrophoneEnabled(newValue)
            _uiState.value = _uiState.value.copy(isMicEnabled = newValue)
        }
    }

    fun toggleCamera(room: Room) {
        viewModelScope.launch {
            val newValue = !_uiState.value.isCameraEnabled
            room.localParticipant.setCameraEnabled(newValue)
            _uiState.value = _uiState.value.copy(isCameraEnabled = newValue)
        }
    }

    fun flipCamera(room: Room) {
        viewModelScope.launch {
            val cameraTrack =
                room.localParticipant
                    .getTrackPublication(Track.Source.CAMERA)
                    ?.track as? LocalVideoTrack
                    ?: return@launch

            cameraTrack.switchCamera()
        }
    }

    fun pinTrack(trackReference: TrackReference) {
        _pinnedTrack.value = trackReference
    }

    fun clearPin() {
        _pinnedTrack.value = null
    }
}

// ─── UI State ─────────────────────────────────────────────────────────────────

data class CallUiState(
    //todo перенести
    val isConnecting: Boolean = true,
    val isMicEnabled: Boolean = true,
    val isCameraEnabled: Boolean = true,
    val participantCount: Int = 0,
)