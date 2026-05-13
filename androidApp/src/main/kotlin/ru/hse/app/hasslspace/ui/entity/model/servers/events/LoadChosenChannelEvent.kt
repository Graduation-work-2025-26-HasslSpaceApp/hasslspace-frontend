package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class LoadChosenChannelEvent {
    data class SuccessLoad(val type: String) : LoadChosenChannelEvent()
    data class Error(val message: String, val exception: Throwable) : LoadChosenChannelEvent()
}
