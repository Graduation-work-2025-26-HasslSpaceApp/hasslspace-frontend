package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class GetServerInvitationsEvent {
    data object SuccessLoad : GetServerInvitationsEvent()
    data class Error(val message: String, val exception: Throwable) : GetServerInvitationsEvent()
}
