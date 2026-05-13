package ru.hse.app.hasslspace.ui.entity.model.auth.events

sealed class LoginUserEvent {
    data object SuccessLogin : LoginUserEvent()
    data class Error(val message: String, val exception: Throwable) : LoginUserEvent()
}
