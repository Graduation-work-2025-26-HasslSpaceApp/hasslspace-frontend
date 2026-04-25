package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class LoadUserFriendsEvent {
    data object SuccessLoad : LoadUserFriendsEvent()
    data class Error(val message: String) : LoadUserFriendsEvent()
}
