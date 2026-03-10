package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class GetUserServersEvent {
    data object SuccessLoad : GetUserServersEvent()
    data class Error(val message: String) : GetUserServersEvent()
}
