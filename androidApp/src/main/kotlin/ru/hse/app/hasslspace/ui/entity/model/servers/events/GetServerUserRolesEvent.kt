package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class GetServerUserRolesEvent {
    data object SuccessLoad : GetServerUserRolesEvent()
    data class Error(val message: String) : GetServerUserRolesEvent()
}
