package ru.hse.app.hasslspace.ui.entity.model.auth.events

sealed class VerifyCodeEvent {
    data object SuccessVerify : VerifyCodeEvent()
    data class Error(val message: String, val exception: Throwable) : VerifyCodeEvent()
}
