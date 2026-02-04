package ru.hse.app.androidApp.ui.navigation

import ru.hse.app.androidApp.R

sealed class BottomNavigationItem(val route: String, val icon: Int, val title: String) {
    data object Servers : BottomNavigationItem("servers", R.drawable.bottom_menu_servers, "Серверы")
    data object Chats : BottomNavigationItem("chats", R.drawable.bottom_menu_chats, "Чаты")
    data object Profile : BottomNavigationItem("profile", R.drawable.bottom_menu_profile, "Профиль")
}