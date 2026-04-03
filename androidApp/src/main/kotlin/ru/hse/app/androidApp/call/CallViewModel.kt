package ru.hse.app.androidApp.call

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

// ─── UI State ─────────────────────────────────────────────────────────────────

data class CallUiState(
    val isConnecting: Boolean = true,
    val isMicEnabled: Boolean = true,
    val isCameraEnabled: Boolean = true,
    val participantCount: Int = 0,
)

// ─── ViewModel ────────────────────────────────────────────────────────────────

class CallViewModel : ViewModel() {

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