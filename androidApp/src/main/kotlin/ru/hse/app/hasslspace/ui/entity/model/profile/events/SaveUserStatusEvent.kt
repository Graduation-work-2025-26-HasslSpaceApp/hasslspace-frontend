package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class SaveUserStatusEvent {
    data object SuccessSave : SaveUserStatusEvent()
    data class Error(val message: String, val exception: Throwable) : SaveUserStatusEvent()
}
