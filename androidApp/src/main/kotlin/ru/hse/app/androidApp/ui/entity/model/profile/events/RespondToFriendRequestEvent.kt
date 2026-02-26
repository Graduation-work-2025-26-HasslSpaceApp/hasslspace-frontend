package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class RespondToFriendRequestEvent {
    data object SuccessRespond : RespondToFriendRequestEvent()
    data class Error(val message: String) : RespondToFriendRequestEvent()
}
