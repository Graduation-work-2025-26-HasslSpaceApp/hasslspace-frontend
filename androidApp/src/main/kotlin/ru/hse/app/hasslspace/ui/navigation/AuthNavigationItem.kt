package ru.hse.app.hasslspace.ui.navigation

sealed class AuthNavigationItem(val route: String, val title: String) {
    data object RegisterScreen : NavigationItem("register", "Регистрация")
    data object LoginScreen : NavigationItem("login", "Вход")
    data object AddPhotoScreen : NavigationItem("add_photo", "Добавить фото профиля")

    data object VerificationScreen : NavigationItem("verification", "Верификация email")
    data object WelcomeScreen : NavigationItem("welcome", "Приветственный экран")
}