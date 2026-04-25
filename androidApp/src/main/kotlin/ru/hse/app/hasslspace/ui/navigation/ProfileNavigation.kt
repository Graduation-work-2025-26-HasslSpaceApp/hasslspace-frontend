//package ru.hse.app.androidApp.ui.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import ru.hse.app.androidApp.screen.profile.AddFriendsScreen
//import ru.hse.app.androidApp.screen.profile.FriendsScreen
//import ru.hse.app.androidApp.screen.profile.ProfileScreen
//import ru.hse.app.androidApp.screen.profile.SettingsScreen
//import ru.hse.app.androidApp.screen.profile.SystemSettingsScreen
//import ru.hse.app.androidApp.screen.profile.UserSettingsScreen
//import ru.hse.app.androidApp.screen.servercard.MainServerScreen
//import ru.hse.app.androidApp.screen.servercard.ServerMembersInfoScreen
//
//@Composable
//fun ProfileNavigation(bottomNavHostController: NavHostController) {
//    val profileNavController = rememberNavController()
////
////    val routeDetailsViewModel: RouteDetailsViewModel = hiltViewModel()
////    val profileViewModel: ProfileViewModel = hiltViewModel()
////
//    NavHost(
//        navController = profileNavController,
//        startDestination = NavigationItem.ProfileMain.route
//    ) {
//        composable(NavigationItem.ProfileMain.route) {
//            ProfileScreen(bottomNavHostController, profileNavController)
//        }
//        composable(NavigationItem.Settings.route) {
//            SettingsScreen(profileNavController)
//        }
//        composable(NavigationItem.UserSettings.route) {
//            UserSettingsScreen(profileNavController)
//        }
//        composable(NavigationItem.SystemSettings.route) {
//            SystemSettingsScreen(profileNavController)
//        }
//        composable(NavigationItem.MyFriends.route) {
//            FriendsScreen(profileNavController)
//        }
//        composable(NavigationItem.AddFriends.route) {
//            AddFriendsScreen(profileNavController)
//        }
//
//        composable(NavigationItem.MainServerScreen.route + "/{serverId}") { backStackEntry ->
//            val serverId = backStackEntry.arguments?.getString("serverId")
//            if (serverId != null) {
//                MainServerScreen(profileNavController, serverId)
//            }
//        }
//
//        composable(NavigationItem.ServerMembersInfo.route + "/{serverId}") { backStackEntry ->
//            val serverId = backStackEntry.arguments?.getString("serverId")
//            if (serverId != null) {
//                ServerMembersInfoScreen(profileNavController, serverId)
//            }
//        }
//    }
//}