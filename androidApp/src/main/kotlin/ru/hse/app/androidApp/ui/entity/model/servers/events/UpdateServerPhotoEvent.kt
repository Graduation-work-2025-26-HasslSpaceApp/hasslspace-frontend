package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class UpdateServerPhotoEvent {
    data object Success : UpdateServerPhotoEvent()
    data class Error(val message: String) : UpdateServerPhotoEvent()
}
