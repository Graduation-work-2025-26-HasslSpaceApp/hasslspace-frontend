package ru.hse.app.androidApp.ui.entity.model.auth

sealed class VerifyCodeEvent {
    data object SuccessVerify : VerifyCodeEvent()
    data class Error(val message: String) : VerifyCodeEvent()
}
