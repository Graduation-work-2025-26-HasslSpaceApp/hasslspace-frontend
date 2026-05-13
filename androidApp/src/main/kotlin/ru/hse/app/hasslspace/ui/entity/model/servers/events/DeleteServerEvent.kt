package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class DeleteServerEvent {
    data object SuccessDelete : DeleteServerEvent()
    data class Error(val message: String, val exception: Throwable) : DeleteServerEvent()
}
