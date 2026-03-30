package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class PatchServerPropertiesEvent {
    data object Success : PatchServerPropertiesEvent()
    data class Error(val message: String) : PatchServerPropertiesEvent()
}
