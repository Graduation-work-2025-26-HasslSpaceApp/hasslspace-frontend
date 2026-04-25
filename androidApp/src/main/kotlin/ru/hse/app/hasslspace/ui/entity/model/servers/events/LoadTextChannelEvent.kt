package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class LoadTextChannelEvent {
    data object SuccessLoad : LoadTextChannelEvent()
    data class Error(val message: String) : LoadTextChannelEvent()
}
