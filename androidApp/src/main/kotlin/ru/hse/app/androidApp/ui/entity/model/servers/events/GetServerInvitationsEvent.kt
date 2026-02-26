package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class GetServerInvitationsEvent {
    data object SuccessLoad : GetServerInvitationsEvent()
    data class Error(val message: String) : GetServerInvitationsEvent()
}
