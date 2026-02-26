package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class SendServerInvitationEvent {
    data object Success : SendServerInvitationEvent()
    data class Error(val message: String) : SendServerInvitationEvent()
}
