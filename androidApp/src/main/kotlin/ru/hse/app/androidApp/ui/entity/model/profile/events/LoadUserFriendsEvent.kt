package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class LoadUserFriendsEvent {
    data object SuccessLoad : LoadUserFriendsEvent()
    data class Error(val message: String) : LoadUserFriendsEvent()
}
