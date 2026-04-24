package ru.hse.app.hasslspace.ui.entity.model.auth.events

sealed class RegisterUserEvent {
    data object SuccessRegister : RegisterUserEvent()
    data class Error(val message: String) : RegisterUserEvent()
}
