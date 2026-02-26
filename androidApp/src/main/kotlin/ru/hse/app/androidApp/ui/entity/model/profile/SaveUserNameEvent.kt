package ru.hse.app.androidApp.ui.entity.model.profile

sealed class SaveUserNameEvent {
    data object SuccessSave : SaveUserNameEvent()
    data class Error(val message: String) : SaveUserNameEvent()
}
