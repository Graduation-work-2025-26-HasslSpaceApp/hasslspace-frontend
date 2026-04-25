package ru.hse.app.hasslspace.ui.entity.model.auth.events

sealed class SavePhotoEvent {
    data object SuccessSave : SavePhotoEvent()
    data class Error(val message: String) : SavePhotoEvent()
}
