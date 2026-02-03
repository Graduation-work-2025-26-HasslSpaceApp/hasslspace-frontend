package ru.hse.app.androidApp.ui.navigation

sealed class NavigationItem(val route: String, val title: String) {
    // Profile
    data object ProfileMain : NavigationItem("profile_main", "Профиль")

    // Servers
    data object ServersMain : NavigationItem("servers_main", "Мои серверы")

    // Chats
    data object ChatsMain : NavigationItem("chats_main", "Мои чаты")

}