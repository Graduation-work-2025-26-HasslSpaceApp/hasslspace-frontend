//package ru.hse.app.androidApp.call.ui
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.safeDrawing
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.windowInsetsPadding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Mic
//import androidx.compose.material.icons.filled.MicOff
//import androidx.compose.material.icons.filled.Videocam
//import androidx.compose.material.icons.filled.VideocamOff
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.FilledTonalIconToggleButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import ru.hse.app.androidApp.CallActivity
//import ru.hse.app.androidApp.call.state.rememberCallPermissions
//import ru.hse.app.androidApp.ui.theme.AppTheme
//
///**
// * Pre-call lobby screen shown before the user joins a room.
// *
// * Lets the user:
// * - Preview their camera (placeholder for now — requires a local camera capture
// *   outside of a RoomScope, which can be added later).
// * - Toggle microphone and camera **before** entering the call so that the
// *   [CallScreen] starts with the desired device state.
// * - Tap "Join" to request permissions (if missing) and navigate to [CallActivity].
// *
// * This composable is intended for use inside a navigation graph or standalone
// * Activity. It does **not** establish a LiveKit connection by itself.
// *
// * @param roomName Display name of the room the user is about to join.
// * @param livekitUrl LiveKit server WebSocket URL.
// * @param token JWT access token.
// * @param onJoined Called after the user taps "Join" and [CallActivity] has been launched.
// */
//@Composable
//fun PreCallScreen(
//    roomName: String,
//    livekitUrl: String,
//    token: String,
//    onJoined: () -> Unit = {},
//) {
//    val context = LocalContext.current
//    val permissions = rememberCallPermissions()
//
//    var isMicEnabled by remember { mutableStateOf(true) }
//    var isCameraEnabled by remember { mutableStateOf(true) }
//    var isJoining by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background)
//            .windowInsetsPadding(WindowInsets.safeDrawing)
//            .padding(24.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Text(
//            text = roomName.ifBlank { "Video Call" },
//            style = MaterialTheme.typography.headlineSmall,
//            color = MaterialTheme.colorScheme.onBackground,
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis,
//            textAlign = TextAlign.Center,
//        )
//
//        Spacer(modifier = Modifier.height(32.dp))
//        Surface(
//            color = MaterialTheme.colorScheme.surfaceContainerLow,
//            shape = RoundedCornerShape(20.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(240.dp),
//        ) {
//            Box(contentAlignment = Alignment.Center) {
//                if (isCameraEnabled) {
//                    Text(
//                        text = "Camera preview",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                    )
//                } else {
//                    Icon(
//                        imageVector = Icons.Default.VideocamOff,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
//                        modifier = Modifier.size(48.dp),
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            // Microphone toggle.
//            FilledTonalIconToggleButton(
//                checked = isMicEnabled,
//                onCheckedChange = { isMicEnabled = it },
//                colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
//                    checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
//                    checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                    containerColor = MaterialTheme.colorScheme.errorContainer,
//                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
//                ),
//                modifier = Modifier.size(56.dp),
//            ) {
//                Icon(
//                    imageVector = if (isMicEnabled) Icons.Default.Mic else Icons.Default.MicOff,
//                    contentDescription = if (isMicEnabled) "Microphone on" else "Microphone off",
//                )
//            }
//
//            Spacer(modifier = Modifier.width(24.dp))
//
//            FilledTonalIconToggleButton(
//                checked = isCameraEnabled,
//                onCheckedChange = { isCameraEnabled = it },
//                colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
//                    checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
//                    checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                    containerColor = MaterialTheme.colorScheme.errorContainer,
//                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
//                ),
//                modifier = Modifier.size(56.dp),
//            ) {
//                Icon(
//                    imageVector = if (isCameraEnabled) Icons.Default.Videocam else Icons.Default.VideocamOff,
//                    contentDescription = if (isCameraEnabled) "Camera on" else "Camera off",
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Text(
//            text = if (!permissions.allGranted) {
//                "Permissions will be requested when you join"
//            } else {
//                "Camera and microphone are ready"
//            },
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.onSurfaceVariant,
//        )
//
//        Spacer(modifier = Modifier.height(40.dp))
//
//        Button(
//            onClick = {
//                isJoining = true
//                if (!permissions.allGranted) {
//                    permissions.requestCallPermissions()
//                }
//                CallActivity.start(
//                    context = context,
//                    url = livekitUrl,
//                    token = token,
//                    roomName = roomName,
//                )
//                onJoined()
//                isJoining = false
//            },
//            enabled = !isJoining,
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.onPrimary,
//            ),
//            shape = RoundedCornerShape(16.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp),
//        ) {
//            if (isJoining) {
//                CircularProgressIndicator(
//                    modifier = Modifier.size(24.dp),
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    strokeWidth = 2.dp,
//                )
//            } else {
//                Text(
//                    text = "Join call",
//                    style = MaterialTheme.typography.titleMedium,
//                )
//            }
//        }
//    }
//}
//
//@Preview(name = "PreCallScreen", showBackground = true, widthDp = 360, heightDp = 640)
//@Composable
//private fun PreviewPreCallScreen() {
//    AppTheme(isDark = false) {
//        PreCallScreen(
//            roomName = "Test Room",
//            livekitUrl = "wss://example.com/livekit",
//            token = "test-token",
//            onJoined = { /* handle onJoined */ }
//        )
//    }
//}
