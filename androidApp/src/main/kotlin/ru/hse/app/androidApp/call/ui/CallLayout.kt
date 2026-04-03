package ru.hse.app.androidApp.call.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.livekit.android.compose.types.TrackReference
import ru.hse.app.androidApp.call.state.rememberPrimaryTrack

@Composable
fun CallLayout(
    roomName: String,
    pinnedTrack: TrackReference?,
    isMicEnabled: Boolean,
    isCameraEnabled: Boolean,
    participantCount: Int,
    onTrackSelected: (TrackReference) -> Unit,
    onPinInvalidated: () -> Unit,
    onToggleMic: () -> Unit,
    onToggleCamera: () -> Unit,
    onFlipCamera: () -> Unit,
    onDisconnect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val primaryTrack = rememberPrimaryTrack(
        pinnedTrack = pinnedTrack,
        onPinInvalidated = onPinInvalidated,
    )

    Column(modifier = modifier.fillMaxSize()) {
        if (primaryTrack != null) {
            PrimarySpeakerView(
                trackReference = primaryTrack,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }

        ParticipantGrid(
            pinnedTrack = pinnedTrack,
            onTrackSelected = onTrackSelected,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(bottom = 8.dp),
        )

        CallControls(
            isMicEnabled = isMicEnabled,
            isCameraEnabled = isCameraEnabled,
            onToggleMic = onToggleMic,
            onToggleCamera = onToggleCamera,
            onFlipCamera = onFlipCamera,
            onDisconnect = onDisconnect,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}