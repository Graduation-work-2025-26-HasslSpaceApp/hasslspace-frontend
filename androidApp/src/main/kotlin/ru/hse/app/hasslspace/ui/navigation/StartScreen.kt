package ru.hse.app.hasslspace.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun StartScreen(
    sessionValidationViewModel: SessionValidationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val isTokenValid = sessionValidationViewModel.isTokenValid.collectAsState()
    val isVerified = sessionValidationViewModel.isVerified.collectAsState()
    val isDarkTheme = sessionValidationViewModel.isDark.collectAsState()

    AppTheme(isDark = isDarkTheme.value) {
        if (isTokenValid.value == true && isVerified.value == true) {
            MainScreen(navController)
        } else {
            AuthNavigation(navController)
        }
    }
}