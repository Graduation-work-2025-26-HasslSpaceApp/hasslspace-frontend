package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class GetServerRolesEvent {
    data object SuccessLoad : GetServerRolesEvent()
    data class Error(val message: String) : GetServerRolesEvent()
}
