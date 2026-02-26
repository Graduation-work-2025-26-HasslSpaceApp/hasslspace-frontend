package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class DeleteChannelEvent {
    data object SuccessDelete : DeleteChannelEvent()
    data class Error(val message: String) : DeleteChannelEvent()
}
