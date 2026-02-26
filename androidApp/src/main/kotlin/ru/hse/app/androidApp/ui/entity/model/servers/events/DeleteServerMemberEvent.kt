package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class DeleteServerMemberEvent {
    data object SuccessDelete : DeleteServerMemberEvent()
    data class Error(val message: String) : DeleteServerMemberEvent()
}
