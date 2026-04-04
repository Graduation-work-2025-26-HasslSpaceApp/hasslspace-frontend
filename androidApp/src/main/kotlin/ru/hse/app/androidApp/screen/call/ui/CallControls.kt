package ru.hse.app.androidApp.screen.call.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.ui.theme.AppTheme

/**
 * Bottom control bar for the call screen.
 *
 * Renders four action buttons:
 * 1. **Microphone** toggle — mic / mic-off icon reflecting [isMicEnabled].
 * 2. **Camera** toggle — videocam / videocam-off icon reflecting [isCameraEnabled].
 * 3. **Flip camera** — switch between front and back cameras.
 * 4. **Disconnect** — red button to leave the call.
 *
 * Buttons use M3 `FilledTonalIconButton` for media toggles and
 * `FilledIconButton` with `error` colour for the disconnect action.
 *
 * @param isMicEnabled Whether the microphone is currently on.
 * @param isCameraEnabled Whether the camera is currently on.
 * @param onToggleMic Called when the mic button is tapped.
 * @param onToggleCamera Called when the camera button is tapped.
 * @param onFlipCamera Called when the flip-camera button is tapped.
 * @param onDisconnect Called when the disconnect button is tapped.
 * @param modifier Layout modifier forwarded to the root [Row].
 */
@Composable
fun CallControls(
    isMicEnabled: Boolean,
    isCameraEnabled: Boolean,
    onToggleMic: () -> Unit,
    onToggleCamera: () -> Unit,
    onFlipCamera: () -> Unit,
    onDisconnect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(BUTTON_SIZE + 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FilledTonalIconButton(
            onClick = onToggleMic,
            colors = if (!isMicEnabled) {
                IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                )
            } else {
                IconButtonDefaults.filledTonalIconButtonColors()
            },
            modifier = Modifier.size(BUTTON_SIZE),
        ) {
            Icon(
                imageVector = if (isMicEnabled) Icons.Default.Mic else Icons.Default.MicOff,
                contentDescription = if (isMicEnabled) "Mute microphone" else "Unmute microphone",
            )
        }

        FilledTonalIconButton(
            onClick = onToggleCamera,
            colors = if (!isCameraEnabled) {
                IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                )
            } else {
                IconButtonDefaults.filledTonalIconButtonColors()
            },
            modifier = Modifier.size(BUTTON_SIZE),
        ) {
            Icon(
                imageVector = if (isCameraEnabled) Icons.Default.Videocam else Icons.Default.VideocamOff,
                contentDescription = if (isCameraEnabled) "Turn off camera" else "Turn on camera",
            )
        }

        FilledTonalIconButton(
            onClick = onFlipCamera,
            modifier = Modifier.size(BUTTON_SIZE),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
        ) {
            Icon(
                imageVector = Icons.Default.Cameraswitch,
                contentDescription = "Switch camera",
            )
        }

        FilledIconButton(
            onClick = onDisconnect,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier.size(BUTTON_SIZE),
        ) {
            Icon(
                imageVector = Icons.Default.CallEnd,
                contentDescription = "End call",
            )
        }
    }
}

private val BUTTON_SIZE = 56.dp

@Preview(showBackground = true)
@Composable
fun CallControlsPreview() {
    AppTheme(isDark = false) {
        CallControls(
            isMicEnabled = true,
            isCameraEnabled = true,
            onToggleMic = {},
            onToggleCamera = {},
            onFlipCamera = {},
            onDisconnect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CallControlsMicOffPreview() {
    AppTheme(isDark = false) {
        CallControls(
            isMicEnabled = false,
            isCameraEnabled = true,
            onToggleMic = {},
            onToggleCamera = {},
            onFlipCamera = {},
            onDisconnect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CallControlsCameraOffPreview() {
    AppTheme(isDark = false) {
        CallControls(
            isMicEnabled = true,
            isCameraEnabled = false,
            onToggleMic = {},
            onToggleCamera = {},
            onFlipCamera = {},
            onDisconnect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CallControlsBothOffPreview() {
    AppTheme(isDark = false) {
        CallControls(
            isMicEnabled = false,
            isCameraEnabled = false,
            onToggleMic = {},
            onToggleCamera = {},
            onFlipCamera = {},
            onDisconnect = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CallControlsDarkPreview() {
    AppTheme(isDark = true) {
        CallControls(
            isMicEnabled = true,
            isCameraEnabled = true,
            onToggleMic = {},
            onToggleCamera = {},
            onFlipCamera = {},
            onDisconnect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CallControlsMicOffPreview1() {
    AppTheme(isDark = true) {
        CallControls(
            isMicEnabled = false,
            isCameraEnabled = true,
            onToggleMic = {},
            onToggleCamera = {},
            onFlipCamera = {},
            onDisconnect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CallControlsCameraOffPreview1() {
    AppTheme(isDark = true) {
        CallControls(
            isMicEnabled = true,
            isCameraEnabled = false,
            onToggleMic = {},
            onToggleCamera = {},
            onFlipCamera = {},
            onDisconnect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CallControlsBothOffPreview1() {
    AppTheme(isDark = true) {
        CallControls(
            isMicEnabled = false,
            isCameraEnabled = false,
            onToggleMic = {},
            onToggleCamera = {},
            onFlipCamera = {},
            onDisconnect = {}
        )
    }
}
