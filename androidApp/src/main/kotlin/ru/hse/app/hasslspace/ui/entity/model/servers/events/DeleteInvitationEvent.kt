package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class DeleteInvitationEvent {
    data object SuccessDelete : DeleteInvitationEvent()
    data class Error(val message: String, val exception: Throwable) : DeleteInvitationEvent()
}
