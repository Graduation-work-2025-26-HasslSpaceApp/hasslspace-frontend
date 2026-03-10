package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class DeleteServerEvent {
    data object SuccessDelete : DeleteServerEvent()
    data class Error(val message: String) : DeleteServerEvent()
}
