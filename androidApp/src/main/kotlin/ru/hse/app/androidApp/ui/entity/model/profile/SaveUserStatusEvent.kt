package ru.hse.app.androidApp.ui.entity.model.profile

sealed class SaveUserStatusEvent {
    data object SuccessSave : SaveUserStatusEvent()
    data class Error(val message: String) : SaveUserStatusEvent()
}
