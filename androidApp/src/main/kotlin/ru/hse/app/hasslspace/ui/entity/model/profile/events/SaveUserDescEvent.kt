package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class SaveUserDescEvent {
    data object SuccessSave : SaveUserDescEvent()
    data class Error(val message: String, val exception: Throwable) : SaveUserDescEvent()
}
