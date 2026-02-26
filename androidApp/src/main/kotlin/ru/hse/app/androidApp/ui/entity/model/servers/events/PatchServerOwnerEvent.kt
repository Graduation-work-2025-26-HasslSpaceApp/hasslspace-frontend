package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class PatchServerOwnerEvent {
    data object SuccessPatch : PatchServerOwnerEvent()
    data class Error(val message: String) : PatchServerOwnerEvent()
}
