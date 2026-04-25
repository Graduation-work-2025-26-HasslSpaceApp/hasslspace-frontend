package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class GetUserServersEvent {
    data object SuccessLoad : GetUserServersEvent()
    data class Error(val message: String) : GetUserServersEvent()
}
