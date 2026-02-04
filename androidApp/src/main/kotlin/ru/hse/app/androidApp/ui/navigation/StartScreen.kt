package ru.hse.app.androidApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun StartScreen(
    sessionValidationViewModel: SessionValidationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isTokenValid = sessionValidationViewModel.isTokenValid.collectAsState()
    val isVerified = sessionValidationViewModel.isVerified.collectAsState()

    if (isTokenValid.value == true && isVerified.value == true) {
        MainScreen(navController)
    } else {
        AuthNavigation(navController)
    }
}