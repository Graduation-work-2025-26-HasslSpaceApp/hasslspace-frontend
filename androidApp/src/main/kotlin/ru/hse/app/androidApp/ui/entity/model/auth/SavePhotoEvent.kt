package ru.hse.app.androidApp.ui.entity.model.auth

sealed class SavePhotoEvent {
    data object SuccessSave : SavePhotoEvent()
    data class Error(val message: String) : SavePhotoEvent()
}
