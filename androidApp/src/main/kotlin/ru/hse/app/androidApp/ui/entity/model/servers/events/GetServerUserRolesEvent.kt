package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class GetServerUserRolesEvent {
    data object SuccessLoad : GetServerUserRolesEvent()
    data class Error(val message: String) : GetServerUserRolesEvent()
}
