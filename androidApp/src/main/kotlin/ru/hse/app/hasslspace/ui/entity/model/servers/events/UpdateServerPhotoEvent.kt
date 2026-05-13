package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class UpdateServerPhotoEvent {
    data object Success : UpdateServerPhotoEvent()
    data class Error(val message: String, val exception: Throwable) : UpdateServerPhotoEvent()
}
