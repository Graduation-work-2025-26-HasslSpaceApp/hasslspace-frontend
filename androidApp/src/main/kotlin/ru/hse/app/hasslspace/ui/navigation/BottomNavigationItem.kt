package ru.hse.app.hasslspace.ui.navigation

import ru.hse.app.hasslspace.R

sealed class BottomNavigationItem(val route: String, val icon: Int, val title: String) {
    data object Servers : BottomNavigationItem("servers", R.drawable.bottom_menu_servers, "Серверы")
    data object Chats : BottomNavigationItem("chats", R.drawable.bottom_menu_chats, "Чаты")
    data object Profile : BottomNavigationItem("profile", R.drawable.bottom_menu_profile, "Профиль")
}