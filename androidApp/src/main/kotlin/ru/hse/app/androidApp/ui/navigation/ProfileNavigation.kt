package ru.hse.app.androidApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.hse.app.androidApp.screen.profile.ProfileScreen
import ru.hse.app.androidApp.screen.profile.SettingsScreen
import ru.hse.app.androidApp.screen.profile.UserSettingsScreen
import ru.hse.app.androidApp.screen.profile.SystemSettingsScreen
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox

@Composable
fun ProfileNavigation(bottomNavHostController: NavHostController) {
    val profileNavController = rememberNavController()
//
//    val routeDetailsViewModel: RouteDetailsViewModel = hiltViewModel()
//    val profileViewModel: ProfileViewModel = hiltViewModel()
//
    NavHost(
        navController = profileNavController,
        startDestination = NavigationItem.ProfileMain.route
    ) {
        composable(NavigationItem.ProfileMain.route) {
            ProfileScreen(profileNavController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(profileNavController)
        }
        composable(NavigationItem.UserSettings.route) {
            UserSettingsScreen(profileNavController)
        }
        composable(NavigationItem.SystemSettings.route) {
            SystemSettingsScreen(profileNavController)
        }
//        composable(NavigationItem.MyFriends.route) {
//            CompletedRoutesScreen(profileNavController)
//        }
//        composable(NavigationItem.MyServers.route) {
//            FavouriteRoutesScreen(profileNavController)
//        }
//        composable(NavigationItem.Settings.route) {
//            EditProfileScreen(profileNavController, profileViewModel)
//        }
//        composable(NavigationItem.AboutProgram.route) {
//            AboutUsScreen(profileNavController, profileViewModel)
//        }
//        composable(NavigationItem.RouteDetails.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RouteDetailsScreen(profileNavController, routeId, routeDetailsViewModel)
//            }
//        }
//        composable(NavigationItem.RoutePassing.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RoutePassingScreen(
//                    bottomNavHostController,
//                    profileNavController,
//                    routeId,
//                    routeDetailsViewModel
//                )
//            }
//        }
//        composable(NavigationItem.RouteReviews.route + "/{routeId}") { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            if (routeId != null) {
//                RouteReviewsScreen(profileNavController, routeId, routeDetailsViewModel)
//            }
//        }
//        composable(
//            route = NavigationItem.RouteRate.route + "/{routeId}/{averageMark}",
//            arguments = listOf(
//                navArgument(name = "routeId") {
//                    type = NavType.StringType
//                },
//                navArgument(name = "averageMark") {
//                    type = NavType.IntType
//                },
//            )
//        ) { backStackEntry ->
//            val routeId = backStackEntry.arguments?.getString("routeId")
//            val mark = backStackEntry.arguments?.getInt("averageMark")
//            if (routeId != null && mark != null) {
//                RateRouteScreen(profileNavController, routeId, mark, routeDetailsViewModel)
//            }
//        }
//    }
    }
}