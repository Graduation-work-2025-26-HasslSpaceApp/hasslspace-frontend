package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class LoadChosenChannelEvent {
    data class SuccessLoad(val type: String) : LoadChosenChannelEvent()
    data class Error(val message: String) : LoadChosenChannelEvent()
}
