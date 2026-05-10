package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class GetServerRolesEvent {
    data object SuccessLoad : GetServerRolesEvent()
    data class Error(val message: String, val exception: Throwable) : GetServerRolesEvent()
}
