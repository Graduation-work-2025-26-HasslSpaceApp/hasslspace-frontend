//package ru.hse.app.androidApp.ui.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import ru.hse.app.androidApp.screen.servercard.MainServerScreen
//import ru.hse.app.androidApp.screen.servercard.ServerMembersInfoScreen
//import ru.hse.app.androidApp.screen.servers.CreateServerScreen
//import ru.hse.app.androidApp.screen.servers.JoinServerScreen
//import ru.hse.app.androidApp.screen.servers.ServersScreen
//import ru.hse.app.androidApp.screen.serversettings.InvitationsScreen
//import ru.hse.app.androidApp.screen.serversettings.MembersScreen
//import ru.hse.app.androidApp.screen.serversettings.RolesScreen
//import ru.hse.app.androidApp.screen.serversettings.ServerSettingsMainScreen
//
//@Composable
//fun ServersNavigation(bottomNavHostController: NavHostController) {
//
//    NavHost(
//        navController = bottomNavHostController,
//        startDestination = NavigationItem.ServersMain.route
//    ) {
//        composable(NavigationItem.ServersMain.route) {
//            ServersScreen(bottomNavHostController)
//        }
//
//        composable(NavigationItem.CreateServer.route) {
//            CreateServerScreen(bottomNavHostController)
//        }
//
//        composable(NavigationItem.JoinServer.route) {
//            JoinServerScreen(bottomNavHostController)
//        }
//
//
//        composable(NavigationItem.MainServerScreen.route + "/{serverId}") { backStackEntry ->
//            val serverId = backStackEntry.arguments?.getString("serverId")
//            if (serverId != null) {
//                MainServerScreen(bottomNavHostController, serverId)
//            }
//        }
//
//        composable(NavigationItem.ServerMembersInfo.route + "/{serverId}") { backStackEntry ->
//            val serverId = backStackEntry.arguments?.getString("serverId")
//            if (serverId != null) {
//                ServerMembersInfoScreen(bottomNavHostController, serverId)
//            }
//        }
//
//        composable(NavigationItem.ServerSettings.route + "/{serverId}") { backStackEntry ->
//            val serverId = backStackEntry.arguments?.getString("serverId")
//            if (serverId != null) {
//                ServerSettingsMainScreen(bottomNavHostController, serverId)
//            }
//        }
//
//        composable(NavigationItem.MembersSettings.route + "/{serverId}") { backStackEntry ->
//            val serverId = backStackEntry.arguments?.getString("serverId")
//            if (serverId != null) {
//                MembersScreen(bottomNavHostController, serverId)
//            }
//        }
//
//        composable(NavigationItem.RolesSettings.route + "/{serverId}") { backStackEntry ->
//            val serverId = backStackEntry.arguments?.getString("serverId")
//            if (serverId != null) {
//                RolesScreen(bottomNavHostController, serverId)
//            }
//        }
//
//        composable(NavigationItem.InvitationsSettings.route + "/{serverId}") { backStackEntry ->
//            val serverId = backStackEntry.arguments?.getString("serverId")
//            if (serverId != null) {
//                InvitationsScreen(bottomNavHostController, serverId)
//            }
//        }
//    }
//}