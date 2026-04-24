package ru.hse.app.hasslspace.ui.entity.model.auth.events

sealed class SendVerificationCodeEvent {
    data object SuccessSend : SendVerificationCodeEvent()
    data class Error(val message: String) : SendVerificationCodeEvent()
}
