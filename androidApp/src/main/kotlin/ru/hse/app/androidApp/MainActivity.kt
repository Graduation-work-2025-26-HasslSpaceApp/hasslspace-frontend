package ru.hse.app.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.hse.app.androidApp.ui.navigation.StartScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestMultiplePermissions()
//    ) { permissions ->
//        val locationPermissionGranted =
//            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
//                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
//        if (locationPermissionGranted) {
//            ToastManager(this).showToast("Разрешение получено")
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        requestLocationPermission()

        // TODO Сделать смену темы тут на уровне всего приложения
        setContent {
            StartScreen()
        }
    }

//    private fun requestLocationPermission() {
//        requestPermissionLauncher.launch(
//            arrayOf(
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//            )
//        )
//    }
}
