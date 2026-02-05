package ru.hse.app.androidApp.ui.entity.model.auth

sealed class LoginUserEvent {
    data object SuccessLogin : LoginUserEvent()
    data class Error(val message: String) : LoginUserEvent()
}
