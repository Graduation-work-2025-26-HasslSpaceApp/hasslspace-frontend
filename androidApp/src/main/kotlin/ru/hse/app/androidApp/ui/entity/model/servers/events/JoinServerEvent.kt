package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class JoinServerEvent {
    data object Success : JoinServerEvent()
    data class Error(val message: String) : JoinServerEvent()
}
