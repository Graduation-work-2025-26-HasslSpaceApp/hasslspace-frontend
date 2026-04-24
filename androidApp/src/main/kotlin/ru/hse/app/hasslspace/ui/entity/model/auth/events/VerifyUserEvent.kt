package ru.hse.app.hasslspace.ui.entity.model.auth.events

sealed class VerifyUserEvent {
    data object SuccessVerify : VerifyUserEvent()
    data class Error(val message: String) : VerifyUserEvent()
}
