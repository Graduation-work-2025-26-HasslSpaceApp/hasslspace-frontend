package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class DeleteServerMemberEvent {
    data object SuccessDelete : DeleteServerMemberEvent()
    data class Error(val message: String, val exception: Throwable) : DeleteServerMemberEvent()
}
