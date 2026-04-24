package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class CreateServerEvent {
    data object SuccessCreate : CreateServerEvent()
    data class Error(val message: String) : CreateServerEvent()
}
