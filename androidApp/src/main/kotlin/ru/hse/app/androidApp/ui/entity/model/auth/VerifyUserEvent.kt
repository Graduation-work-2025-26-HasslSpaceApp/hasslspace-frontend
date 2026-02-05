package ru.hse.app.androidApp.ui.entity.model.auth

sealed class VerifyUserEvent {
    data object SuccessVerify : VerifyUserEvent()
    data class Error(val message: String) : VerifyUserEvent()
}
