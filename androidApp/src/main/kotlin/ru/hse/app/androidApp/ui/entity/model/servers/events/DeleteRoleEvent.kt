package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class DeleteRoleEvent {
    data object SuccessDelete : DeleteRoleEvent()
    data class Error(val message: String) : DeleteRoleEvent()
}
