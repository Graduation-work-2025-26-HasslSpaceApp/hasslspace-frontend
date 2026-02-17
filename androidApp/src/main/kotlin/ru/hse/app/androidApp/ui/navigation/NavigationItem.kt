package ru.hse.app.androidApp.ui.navigation

sealed class NavigationItem(val route: String, val title: String) {
    // Profile
    data object ProfileMain : NavigationItem("profile_main", "Профиль")
    data object MyFriends : NavigationItem("my_friends", "Мои друзья")
    data object MyServers : NavigationItem("my_servers", "Мои серверы")
    data object Settings : NavigationItem("settings", "Настройки")

    // Servers
    data object ServersMain : NavigationItem("servers_main", "Мои серверы")

    // Chats
    data object ChatsMain : NavigationItem("chats_main", "Мои чаты")

}