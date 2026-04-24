package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class CreateServerRoleEvent {
    data object SuccessCreate : CreateServerRoleEvent()
    data class Error(val message: String) : CreateServerRoleEvent()
}
