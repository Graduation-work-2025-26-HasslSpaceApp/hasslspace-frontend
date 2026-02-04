package ru.hse.app.androidApp.ui.entity.model.auth

sealed class SendVerificationCodeEvent {
    data object SuccessSend : SendVerificationCodeEvent()
    data class Error(val message: String) : SendVerificationCodeEvent()
}
