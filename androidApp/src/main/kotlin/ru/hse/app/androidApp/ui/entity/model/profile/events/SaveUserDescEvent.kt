package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class SaveUserDescEvent {
    data object SuccessSave : SaveUserDescEvent()
    data class Error(val message: String) : SaveUserDescEvent()
}
