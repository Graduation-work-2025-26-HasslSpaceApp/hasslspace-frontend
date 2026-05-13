package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class CreateFriendRequestEvent {
    data class SuccessRequest(val nickname: String) : CreateFriendRequestEvent()
    data class Error(val message: String, val exception: Throwable) : CreateFriendRequestEvent()
}
