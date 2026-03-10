package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class DeleteServerInvitationEvent {
    data object SuccessDelete : DeleteServerInvitationEvent()
    data class Error(val message: String) : DeleteServerInvitationEvent()
}
