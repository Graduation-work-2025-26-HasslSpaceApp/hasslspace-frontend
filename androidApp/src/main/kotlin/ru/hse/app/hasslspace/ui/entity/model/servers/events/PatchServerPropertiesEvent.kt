package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class PatchServerPropertiesEvent {
    data object Success : PatchServerPropertiesEvent()
    data class Error(val message: String) : PatchServerPropertiesEvent()
}
