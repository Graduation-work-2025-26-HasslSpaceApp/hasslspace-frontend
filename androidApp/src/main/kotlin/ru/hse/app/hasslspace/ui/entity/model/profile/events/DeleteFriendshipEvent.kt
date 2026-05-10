package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class DeleteFriendshipEvent {
    data object SuccessDelete : DeleteFriendshipEvent()
    data class Error(val message: String, val exception: Throwable) : DeleteFriendshipEvent()
}
