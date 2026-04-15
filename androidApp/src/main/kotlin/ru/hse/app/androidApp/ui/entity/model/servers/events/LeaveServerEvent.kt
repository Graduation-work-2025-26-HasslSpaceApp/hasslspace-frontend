package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class LeaveServerEvent {
    data object Success : LeaveServerEvent()
    data class Error(val message: String) : LeaveServerEvent()
}
