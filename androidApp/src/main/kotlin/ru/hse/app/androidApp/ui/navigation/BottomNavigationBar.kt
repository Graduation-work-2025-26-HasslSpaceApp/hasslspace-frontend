package ru.hse.app.androidApp.ui.navigation

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Servers,
        BottomNavigationItem.Chats,
        BottomNavigationItem.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.height(70.dp)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 10.dp)
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
        composable(BottomNavigationItem.Servers.route) { ServersNavigation(bottomNavController) }
        composable(BottomNavigationItem.Chats.route) { ChatsNavigation(bottomNavController) }
        composable(BottomNavigationItem.Profile.route) { ProfileNavigation(bottomNavController) }
    }
}


@Composable
fun MainScreen(
    navController: NavHostController
) {

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
            BottomNavigation(navController)
        }
    }
}

