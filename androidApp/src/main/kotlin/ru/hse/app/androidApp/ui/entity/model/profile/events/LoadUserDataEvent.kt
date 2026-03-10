package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class LoadUserDataEvent {
    data object SuccessLoad : LoadUserDataEvent()
    data class Error(val message: String) : LoadUserDataEvent()
}
