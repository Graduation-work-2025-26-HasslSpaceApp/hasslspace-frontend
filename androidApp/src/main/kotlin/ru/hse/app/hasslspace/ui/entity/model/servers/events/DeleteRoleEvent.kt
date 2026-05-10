package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class DeleteRoleEvent {
    data object SuccessDelete : DeleteRoleEvent()
    data class Error(val message: String, val exception: Throwable) : DeleteRoleEvent()
}
