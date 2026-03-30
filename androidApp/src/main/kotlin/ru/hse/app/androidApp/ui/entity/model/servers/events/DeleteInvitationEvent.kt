package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class DeleteInvitationEvent {
    data object SuccessDelete : DeleteInvitationEvent()
    data class Error(val message: String) : DeleteInvitationEvent()
}
