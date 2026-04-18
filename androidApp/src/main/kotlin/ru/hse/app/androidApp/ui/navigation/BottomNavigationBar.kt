package ru.hse.app.androidApp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import ru.hse.app.androidApp.BuildConfig
import ru.hse.app.androidApp.screen.call.CallScreen
import ru.hse.app.androidApp.screen.channel.TextChannelScreen
import ru.hse.app.androidApp.screen.chats.ChatScreen
import ru.hse.app.androidApp.screen.chats.ChatsScreen
import ru.hse.app.androidApp.screen.chats.NewMessageScreen
import ru.hse.app.androidApp.screen.profile.AddFriendsScreen
import ru.hse.app.androidApp.screen.profile.FriendsScreen
import ru.hse.app.androidApp.screen.profile.ProfileScreen
import ru.hse.app.androidApp.screen.profile.SettingsScreen
import ru.hse.app.androidApp.screen.profile.SystemSettingsScreen
import ru.hse.app.androidApp.screen.profile.UserSettingsScreen
import ru.hse.app.androidApp.screen.servercard.MainServerScreen
import ru.hse.app.androidApp.screen.servercard.ServerMembersInfoScreen
import ru.hse.app.androidApp.screen.servers.CreateServerScreen
import ru.hse.app.androidApp.screen.servers.JoinServerScreen
import ru.hse.app.androidApp.screen.servers.ServersScreen
import ru.hse.app.androidApp.screen.serversettings.InvitationsScreen
import ru.hse.app.androidApp.screen.serversettings.MembersScreen
import ru.hse.app.androidApp.screen.serversettings.RolesScreen
import ru.hse.app.androidApp.screen.serversettings.ServerSettingsMainScreen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Servers,
        BottomNavigationItem.Chats,
        BottomNavigationItem.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.height(60.dp)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 5.dp)
                    .size(50.dp),
                onClick = {
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.outline,
                    indicatorColor = Color.Transparent
                )
            )

        }
    }
}


@Composable
fun BottomNavigation(bottomNavController: NavHostController) {
    NavHost(bottomNavController, startDestination = BottomNavigationItem.Servers.route) {
        composable(BottomNavigationItem.Servers.route) { ServersScreen(bottomNavController) }
        composable(BottomNavigationItem.Chats.route) { ChatsScreen(bottomNavController) }
        composable(BottomNavigationItem.Profile.route) { ProfileScreen(bottomNavController) }

        // Servers
        composable(NavigationItem.ServersMain.route) {
            ServersScreen(bottomNavController)
        }

        composable(NavigationItem.CreateServer.route) {
            CreateServerScreen(bottomNavController)
        }

        composable(NavigationItem.JoinServer.route) {
            JoinServerScreen(bottomNavController)
        }


        composable(NavigationItem.MainServerScreen.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                MainServerScreen(bottomNavController, serverId)
            }
        }

        composable(NavigationItem.ServerMembersInfo.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                ServerMembersInfoScreen(bottomNavController, serverId)
            }
        }

        composable(NavigationItem.ServerSettings.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                ServerSettingsMainScreen(bottomNavController, serverId)
            }
        }

        composable(NavigationItem.MembersSettings.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                MembersScreen(bottomNavController, serverId)
            }
        }

        composable(NavigationItem.RolesSettings.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                RolesScreen(bottomNavController, serverId)
            }
        }

        composable(NavigationItem.InvitationsSettings.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                InvitationsScreen(bottomNavController, serverId)
            }
        }

        // Chats

        composable(NavigationItem.ChatsMain.route) {
            ChatsScreen(bottomNavController)
        }

        composable(NavigationItem.Chat.route + "/{chatId}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
            if (chatId != null) {
                ChatScreen(chatId, bottomNavController)
            }
        }

        composable(
            route = NavigationItem.TextChannelChat.route + "/{serverId}/{chatId}/{curUserId}",
            arguments = listOf(
                navArgument(name = "serverId") {
                    type = NavType.StringType
                },
                navArgument(name = "chatId") {
                    type = NavType.StringType
                },
                navArgument(name = "curUserId") {
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            val chatId = backStackEntry.arguments?.getString("chatId")
            val curUserId = backStackEntry.arguments?.getString("curUserId")


            if (serverId != null && chatId != null && curUserId != null) {
                TextChannelScreen(serverId, chatId, curUserId, bottomNavController)
            }
        }

        composable(NavigationItem.NewMessageScreen.route) {
            NewMessageScreen(bottomNavController)
        }

        // Profile

        composable(NavigationItem.ProfileMain.route) {
            ProfileScreen(bottomNavController)
        }
        composable(NavigationItem.Settings.route) {
            SettingsScreen(bottomNavController)
        }
        composable(NavigationItem.UserSettings.route) {
            UserSettingsScreen(bottomNavController)
        }
        composable(NavigationItem.SystemSettings.route) {
            SystemSettingsScreen(bottomNavController)
        }
        composable(NavigationItem.MyFriends.route) {
            FriendsScreen(bottomNavController)
        }
        composable(NavigationItem.AddFriends.route) {
            AddFriendsScreen(bottomNavController)
        }

        composable(NavigationItem.MainServerScreen.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                MainServerScreen(bottomNavController, serverId)
            }
        }

        composable(NavigationItem.ServerMembersInfo.route + "/{serverId}") { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId")
            if (serverId != null) {
                ServerMembersInfoScreen(bottomNavController, serverId)
            }
        }

        // Voice Rooms
        composable(
            route = NavigationItem.VoiceRoom.route + "/{token}/{roomName}/{videoEnabled}/{limit}",
            arguments = listOf(
                navArgument(name = "token") {
                    type = NavType.StringType
                },
                navArgument(name = "roomName") {
                    type = NavType.StringType
                },
                navArgument(name = "videoEnabled") {
                    type = NavType.BoolType
                },
                navArgument(name = "limit") {
                    type = NavType.IntType
                },
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token")
            val roomName = backStackEntry.arguments?.getString("roomName")
            val videoEnabled = backStackEntry.arguments?.getBoolean("videoEnabled") ?: false
            val limit = backStackEntry.arguments?.getInt("limit")



            if (token != null && roomName != null) {
                CallScreen(
                    url = BuildConfig.LIVEKIT_URL,
                    token = token,
                    roomName = roomName,
                    limit = limit,
                    videoEnabled = videoEnabled,
                    navController = bottomNavController,
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController
) {

    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = null)
    val currentRoute = currentBackStackEntry?.destination?.route

    val isChatOrVoiceScreen =
        currentRoute?.startsWith(NavigationItem.Chat.route.substringBefore("/{")) == true ||
                currentRoute?.startsWith(NavigationItem.VoiceRoom.route.substringBefore("/{")) == true ||
                currentRoute?.startsWith(NavigationItem.TextChannelChat.route.substringBefore("/{")) == true


    Scaffold(
        bottomBar = {
            if (!isChatOrVoiceScreen) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (!isChatOrVoiceScreen) innerPadding.calculateBottomPadding() else 0.dp)
        ) {
            BottomNavigation(navController)
        }
    }
}

