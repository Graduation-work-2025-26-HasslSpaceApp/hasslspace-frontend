package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class SendServerInvitationEvent {
    data class Success(val userId: String) : SendServerInvitationEvent()
    data class Error(val message: String, val exception: Throwable) : SendServerInvitationEvent()
}
