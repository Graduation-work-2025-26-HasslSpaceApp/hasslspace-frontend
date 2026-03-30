package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class PatchChannelEvent {
    data object Success : PatchChannelEvent()
    data class Error(val message: String) : PatchChannelEvent()
}
