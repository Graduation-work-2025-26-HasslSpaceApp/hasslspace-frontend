//package ru.hse.app.androidApp
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.WindowManager
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import ru.hse.app.androidApp.CallActivity.Companion.start
//import ru.hse.app.androidApp.ui.theme.AppTheme
//
///**
// * Activity that hosts the LiveKit video call screen.
// *
// * Manages the call lifecycle independently from the main [AppActivity]:
// * - Keeps the screen awake during an active call via [WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON].
// * - Receives connection parameters (server URL, JWT token, room name) through intent extras.
// * - Wraps the Compose UI in the project's [AppTheme] so that M3 tokens are consistent with
// *   the rest of the application.
// *
// * Launch this activity via the [start] helper:
// * ```kotlin
// * CallActivity.start(context, url = "wss://…", token = "jwt…", roomName = "general")
// * ```
// */
//class CallActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//
//        val url = intent.getStringExtra(EXTRA_URL) ?: run { finish(); return }
//        val token = intent.getStringExtra(EXTRA_TOKEN) ?: run { finish(); return }
//        val roomName = intent.getStringExtra(EXTRA_ROOM_NAME).orEmpty()
//
//        setContent {
////            AppTheme(onThemeChanged = {}) {
////                CallScreen(
////                    url = url,
////                    token = token,
////                    roomName = roomName,
////                    onDisconnect = ::finish,
////                )
//        }
//    }
//
//    override fun onDestroy() {
//        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        super.onDestroy()
//    }
//
//    companion object {
//        /** LiveKit WebSocket URL, e.g. `wss://my-server.livekit.cloud`. */
//        const val EXTRA_URL = "extra_url"
//
//        /** JWT access token obtained from the backend. */
//        const val EXTRA_TOKEN = "extra_token"
//
//        /** Human-readable room name (used for the toolbar title). */
//        const val EXTRA_ROOM_NAME = "extra_room_name"
//
//        /**
//         * Convenience launcher for [CallActivity].
//         *
//         * @param context Calling context.
//         * @param url LiveKit server WebSocket URL.
//         * @param token JWT access token for the room.
//         * @param roomName Display name of the room.
//         */
//        fun start(context: Context, url: String, token: String, roomName: String) {
//            val intent = Intent(context, CallActivity::class.java).apply {
//                putExtra(EXTRA_URL, url)
//                putExtra(EXTRA_TOKEN, token)
//                putExtra(EXTRA_ROOM_NAME, roomName)
//            }
//            context.startActivity(intent)
//        }
//    }
//}
