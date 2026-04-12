package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class LoadChosenServerRolesEvent {
    data object SuccessLoad : LoadChosenServerRolesEvent()
    data class Error(val message: String) : LoadChosenServerRolesEvent()
}
