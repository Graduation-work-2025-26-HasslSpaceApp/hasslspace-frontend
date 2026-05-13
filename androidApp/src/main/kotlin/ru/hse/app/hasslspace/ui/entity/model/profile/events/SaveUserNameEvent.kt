package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class SaveUserNameEvent {
    data object SuccessSave : SaveUserNameEvent()
    data class Error(val message: String, val exception: Throwable) : SaveUserNameEvent()
}
