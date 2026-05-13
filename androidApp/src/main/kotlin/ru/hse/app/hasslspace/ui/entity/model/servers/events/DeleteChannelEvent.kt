package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class DeleteChannelEvent {
    data object SuccessDelete : DeleteChannelEvent()
    data class Error(val message: String, val exception: Throwable) : DeleteChannelEvent()
}
