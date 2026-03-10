package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class SendServerInvitationEvent {
    data class Success(val userId: String) : SendServerInvitationEvent()
    data class Error(val message: String) : SendServerInvitationEvent()
}
