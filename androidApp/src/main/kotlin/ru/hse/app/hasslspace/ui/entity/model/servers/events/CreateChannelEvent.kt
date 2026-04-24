package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class CreateChannelEvent {
    data object SuccessCreate : CreateChannelEvent()
    data class Error(val message: String) : CreateChannelEvent()
}
