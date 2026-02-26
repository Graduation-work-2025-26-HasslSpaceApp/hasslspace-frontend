package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class CreateFriendRequestEvent {
    data class SuccessRequest(val nickname: String) : CreateFriendRequestEvent()
    data class Error(val message: String) : CreateFriendRequestEvent()
}
