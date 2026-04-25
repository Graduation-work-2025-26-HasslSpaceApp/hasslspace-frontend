package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class PatchChannelEvent {
    data object Success : PatchChannelEvent()
    data class Error(val message: String) : PatchChannelEvent()
}
