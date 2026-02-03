package ru.hse.app.androidApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AuthNavigation(authNavController: NavHostController) {
//    val authViewModel: AuthViewModel = hiltViewModel()
//
//    NavHost(authNavController, startDestination = AuthNavigationItem.WelcomeScreen.route) {
//        composable(AuthNavigationItem.RegisterScreen.route) {
//            authViewModel.clear()
//            RegisterScreen(authNavController, authViewModel)
//        }
//        composable(AuthNavigationItem.LoginScreen.route) {
//            authViewModel.clear()
//            LoginScreen(authNavController, authViewModel)
//        }
//        composable(AuthNavigationItem.AddPhotoScreen.route) {
//            AddPhotoScreen(authNavController, authViewModel)
//        }
//        composable(AuthNavigationItem.VerificationScreen.route + "/{type}") { backStackEntry ->
//            val type = backStackEntry.arguments?.getString("type")
//            if (type != null) {
//                VerificationScreen(authNavController, type, authViewModel)
//            }
//        }
//        composable(AuthNavigationItem.WelcomeScreen.route) {
//            WelcomeScreen(authNavController)
//        }
//    }
}



