package ru.hse.app.androidApp.ui.entity.model.auth

sealed class RegisterUserEvent {
    data object SuccessRegister : RegisterUserEvent()
    data class Error(val message: String) : RegisterUserEvent()
}
