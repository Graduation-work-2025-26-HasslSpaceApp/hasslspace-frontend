package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class PatchServerOwnerEvent {
    data object SuccessPatch : PatchServerOwnerEvent()
    data class Error(val message: String) : PatchServerOwnerEvent()
}
