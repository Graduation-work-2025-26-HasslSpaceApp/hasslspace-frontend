package ru.hse.app.androidApp.screen.call.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.livekit.android.compose.types.TrackReference
import io.livekit.android.room.participant.Participant
import ru.hse.app.androidApp.screen.call.state.rememberPrimaryTrack

@Composable
fun CallLayout(
    roomName: String,
    pinnedTrack: TrackReference?,
    currentUserIdentity: Participant.Identity?,
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
        currentUserIdentity = currentUserIdentity,
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 272.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = roomName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "$participantCount участников",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            if (primaryTrack != null) {
//                Box(
//                    modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.primary)
//                )
                PrimarySpeakerView(
                    trackReference = primaryTrack,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            ParticipantGrid(
                pinnedTrack = pinnedTrack,
                onTrackSelected = onTrackSelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 8.dp),
            )

            CallControls(
                isMicEnabled = isMicEnabled,
                isCameraEnabled = isCameraEnabled,
                onToggleMic = onToggleMic,
                onToggleCamera = onToggleCamera,
                onFlipCamera = onFlipCamera,
                onDisconnect = onDisconnect,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            )
        }

    }
}