package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class GetServerInfoEvent {
    data object SuccessLoad : GetServerInfoEvent()
    data class Error(val message: String) : GetServerInfoEvent()
}
