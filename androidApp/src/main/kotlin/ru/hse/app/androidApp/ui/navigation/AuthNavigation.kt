package ru.hse.app.androidApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.hse.app.androidApp.screen.auth.AddPhotoScreen
import ru.hse.app.androidApp.screen.auth.AuthViewModel
import ru.hse.app.androidApp.screen.auth.LoginScreen
import ru.hse.app.androidApp.screen.auth.RegisterScreen
import ru.hse.app.androidApp.screen.auth.VerificationScreen
import ru.hse.app.androidApp.screen.auth.WelcomeScreen

@Composable
fun AuthNavigation(authNavController: NavHostController) {
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(authNavController, startDestination = AuthNavigationItem.WelcomeScreen.route) {
        composable(AuthNavigationItem.RegisterScreen.route) {
            authViewModel.clear()
            RegisterScreen(authNavController, authViewModel)
        }
        composable(AuthNavigationItem.LoginScreen.route) {
            authViewModel.clear()
            LoginScreen(authNavController, authViewModel)
        }
        composable(AuthNavigationItem.AddPhotoScreen.route) {
            AddPhotoScreen(authNavController, authViewModel)
        }
        composable(AuthNavigationItem.VerificationScreen.route + "/{type}") { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            if (type != null) {
                VerificationScreen(authNavController, type, authViewModel)
            }
        }
        composable(AuthNavigationItem.WelcomeScreen.route) {
            //AddPhotoScreen(authNavController, authViewModel)
            //VerificationScreen(authNavController, "login", authViewModel)
            WelcomeScreen(authNavController)
        }
    }
}



