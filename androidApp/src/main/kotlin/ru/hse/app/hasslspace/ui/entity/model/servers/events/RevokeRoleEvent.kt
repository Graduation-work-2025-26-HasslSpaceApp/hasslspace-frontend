package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class RevokeRoleEvent {
    data object Success : RevokeRoleEvent()
    data class Error(val message: String) : RevokeRoleEvent()
}
