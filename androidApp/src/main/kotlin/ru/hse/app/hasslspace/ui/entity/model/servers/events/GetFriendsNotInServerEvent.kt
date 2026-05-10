package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class GetFriendsNotInServerEvent {
    data object SuccessLoad : GetFriendsNotInServerEvent()
    data class Error(val message: String, val exception: Throwable) : GetFriendsNotInServerEvent()
}
