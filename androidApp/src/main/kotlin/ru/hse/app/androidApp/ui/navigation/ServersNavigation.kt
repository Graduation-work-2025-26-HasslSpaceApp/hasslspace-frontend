package ru.hse.app.androidApp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.hse.app.androidApp.screen.servers.CreateServerScreen
import ru.hse.app.androidApp.screen.servers.JoinServerScreen
import ru.hse.app.androidApp.screen.servercard.MainServerScreen
import ru.hse.app.androidApp.screen.servercard.ServerMembersInfoScreen
import ru.hse.app.androidApp.screen.servers.ServersScreen
import ru.hse.app.androidApp.screen.serversettings.MembersScreen
import ru.hse.app.androidApp.screen.serversettings.RolesScreen
import ru.hse.app.androidApp.screen.serversettings.ServerSettingsMainScreen

@Composable
fun ServersNavigation(bottomNavHostController: NavHostController) {
    val serversNavController = rememberNavController()

    NavHost(
        navController = serversNavController,
        startDestination = NavigationItem.ServersMain.route
    ) {
        composable(NavigationItem.ServersMain.route) {
            ServersScreen(serversNavController)
        }

        composable(NavigationItem.CreateServer.route) {
            CreateServerScreen(serversNavController)
        }

        composable(NavigationItem.JoinServer.route) {
            JoinServerScreen(serversNavController)
        }


        composable(NavigationItem.MainServerScreen.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                MainServerScreen(serversNavController, serverId)
            }
        }

        composable(NavigationItem.ServerMembersInfo.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                ServerMembersInfoScreen(serversNavController, serverId)
            }
        }

        composable(NavigationItem.ServerSettings.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                ServerSettingsMainScreen(serversNavController, serverId)
            }
        }

        composable(NavigationItem.MembersSettings.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                MembersScreen(serversNavController, serverId)
            }
        }

        composable(NavigationItem.RolesSettings.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                RolesScreen(serversNavController, serverId)
            }
        }
    }
}