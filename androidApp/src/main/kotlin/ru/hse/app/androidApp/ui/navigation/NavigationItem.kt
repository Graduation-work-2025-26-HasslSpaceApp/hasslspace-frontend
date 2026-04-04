package ru.hse.app.androidApp.ui.navigation

sealed class NavigationItem(val route: String, val title: String) {
    // Profile
    data object ProfileMain : NavigationItem("profile_main", "Профиль")
    data object MyFriends : NavigationItem("my_friends", "Мои друзья")

    data object AddFriends : NavigationItem("add_friends", "Добавить в друзья")
    data object MyServers : NavigationItem("my_servers", "Мои серверы")
    data object Settings : NavigationItem("settings", "Настройки")
    data object UserSettings : NavigationItem("user_settings", "Пользовательские настройки")
    data object SystemSettings : NavigationItem("system_settings", "Системные настройки")

    // Servers
    data object ServersMain : NavigationItem("servers_main", "Мои серверы")
    data object CreateServer : NavigationItem("create_server", "Создать сервер")
    data object JoinServer : NavigationItem("join_server", "Присоединиться к серверу")
    data object MainServerScreen : NavigationItem("main_server", "Главная страница сервера")
    data object ServerMembersInfo :
        NavigationItem("server_members_info", "Страница с информацией об участниках сервера")

    data object ServerSettings : NavigationItem("server_settings", "Редактирование сервера")
    data object MembersSettings :
        NavigationItem("server_members_settings", "Редактирование участников сервера")

    data object RolesSettings :
        NavigationItem("server_roles_settings", "Редактирование ролей сервера")

    data object InvitationsSettings :
        NavigationItem("server_invitations_settings", "Редактирование приглашений сервера")

    // Chats
    data object ChatsMain : NavigationItem("chats_main", "Мои чаты")
    data object Chat : NavigationItem("chat_bubble_view", "Страница чата")

    // Voice Rooms

    data object VoiceRoom : NavigationItem("voice_room", "Voice Room")

}