package ru.hse.app.androidApp.ui.entity.model.auth.events

sealed class CheckEmailVerificationEvent {
    data class SuccessChecked(val verified: Boolean) : CheckEmailVerificationEvent()
    data class Error(val message: String) : CheckEmailVerificationEvent()
}
