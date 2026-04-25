package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class GetServerInfoEvent {
    data object SuccessLoad : GetServerInfoEvent()
    data class Error(val message: String) : GetServerInfoEvent()
}
