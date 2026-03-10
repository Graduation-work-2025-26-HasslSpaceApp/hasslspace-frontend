package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class DeleteFriendshipEvent {
    data object SuccessDelete : DeleteFriendshipEvent()
    data class Error(val message: String) : DeleteFriendshipEvent()
}
