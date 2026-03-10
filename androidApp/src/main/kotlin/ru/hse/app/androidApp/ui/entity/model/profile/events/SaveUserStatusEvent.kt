package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class SaveUserStatusEvent {
    data object SuccessSave : SaveUserStatusEvent()
    data class Error(val message: String) : SaveUserStatusEvent()
}
