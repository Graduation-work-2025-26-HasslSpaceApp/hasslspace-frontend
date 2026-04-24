package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class AssignRoleEvent {
    data object Success : AssignRoleEvent()
    data class Error(val message: String) : AssignRoleEvent()
}
