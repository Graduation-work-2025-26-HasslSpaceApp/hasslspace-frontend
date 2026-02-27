package ru.hse.app.androidApp.ui.entity.model.servers.events

sealed class GetFriendsNotInServerEvent {
    data object SuccessLoad : GetFriendsNotInServerEvent()
    data class Error(val message: String) : GetFriendsNotInServerEvent()
}
