package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class PatchServerRoleEvent {
    data object Success : PatchServerRoleEvent()
    data class Error(val message: String) : PatchServerRoleEvent()
}
