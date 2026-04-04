package ru.hse.app.androidApp.screen.call.state

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

/**
 * Aggregated runtime-permission state required for a video call.
 *
 * Wraps [Accompanist Permissions][com.google.accompanist.permissions] to expose
 * a simple API for the call UI:
 *
 * - [hasCameraPermission] / [hasMicPermission] — read these before enabling tracks.
 * - [requestCallPermissions] — triggers the system permission dialog for both
 *   camera **and** microphone at once.
 * - [requestCameraPermission] / [requestMicPermission] — request a single permission
 *   (e.g. when the user taps a currently-disabled toggle).
 *
 * Bluetooth connect permission (required on Android 12+ for headset routing) is
 * included automatically.
 */
@Stable
class CallPermissionState @OptIn(ExperimentalPermissionsApi::class) constructor(
    private val cameraPermission: PermissionState,
    private val micPermission: PermissionState,
    private val allPermissions: MultiplePermissionsState,
) {
    /** True when [Manifest.permission.CAMERA] has been granted. */
    @OptIn(ExperimentalPermissionsApi::class)
    val hasCameraPermission: Boolean
        get() = cameraPermission.status.isGranted

    /** True when [Manifest.permission.RECORD_AUDIO] has been granted. */
    @OptIn(ExperimentalPermissionsApi::class)
    val hasMicPermission: Boolean
        get() = micPermission.status.isGranted

    /** True when both camera and microphone permissions are granted. */
    @OptIn(ExperimentalPermissionsApi::class)
    val allGranted: Boolean
        get() = allPermissions.allPermissionsGranted

    /** Request camera + microphone (+ bluetooth on API 31+) permissions at once. */
    @OptIn(ExperimentalPermissionsApi::class)
    fun requestCallPermissions() {
        allPermissions.launchMultiplePermissionRequest()
    }

    /** Request only the camera permission. */
    @OptIn(ExperimentalPermissionsApi::class)
    fun requestCameraPermission() {
        cameraPermission.launchPermissionRequest()
    }

    /** Request only the microphone permission. */
    @OptIn(ExperimentalPermissionsApi::class)
    fun requestMicPermission() {
        micPermission.launchPermissionRequest()
    }
}

/**
 * Remember the aggregated [CallPermissionState] for the call screen.
 *
 * Must be called from a `@Composable` hosted inside an Activity (not a Service).
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberCallPermissions(): CallPermissionState {
    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    val micPermission = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val requiredPermissions = remember {
        buildList {
            add(Manifest.permission.CAMERA)
            add(Manifest.permission.RECORD_AUDIO)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_CONNECT)
            }
        }
    }

    val allPermissions = rememberMultiplePermissionsState(requiredPermissions)

    return remember(cameraPermission, micPermission, allPermissions) {
        CallPermissionState(
            cameraPermission = cameraPermission,
            micPermission = micPermission,
            allPermissions = allPermissions,
        )
    }
}

/**
 * Returns `true` when the microphone can be enabled:
 * user wants it **and** the runtime permission is granted.
 */
@Composable
fun rememberEnableMic(userWantsMic: Boolean): Boolean {
    val permissions = rememberCallPermissions()
    val enabled by remember(userWantsMic) {
        derivedStateOf { userWantsMic && permissions.hasMicPermission }
    }
    return enabled
}

/**
 * Returns `true` when the camera can be enabled:
 * user wants it **and** the runtime permission is granted.
 */
@Composable
fun rememberEnableCamera(userWantsCamera: Boolean): Boolean {
    val permissions = rememberCallPermissions()
    val enabled by remember(userWantsCamera) {
        derivedStateOf { userWantsCamera && permissions.hasCameraPermission }
    }
    return enabled
}
