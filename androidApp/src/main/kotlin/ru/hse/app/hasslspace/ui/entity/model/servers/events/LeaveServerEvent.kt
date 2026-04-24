package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class LeaveServerEvent {
    data object Success : LeaveServerEvent()
    data class Error(val message: String) : LeaveServerEvent()
}
