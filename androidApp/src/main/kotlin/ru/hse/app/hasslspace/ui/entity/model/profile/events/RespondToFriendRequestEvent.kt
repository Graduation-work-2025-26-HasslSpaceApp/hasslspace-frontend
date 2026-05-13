package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class RespondToFriendRequestEvent {
    data object SuccessRespond : RespondToFriendRequestEvent()
    data class Error(val message: String, val exception: Throwable) : RespondToFriendRequestEvent()
}
