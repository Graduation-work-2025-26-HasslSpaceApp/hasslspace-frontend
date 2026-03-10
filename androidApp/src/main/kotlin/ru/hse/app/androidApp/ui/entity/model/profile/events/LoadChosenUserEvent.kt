package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class LoadChosenUserEvent {
    data object SuccessLoad : LoadChosenUserEvent()
    data class Error(val message: String) : LoadChosenUserEvent()
}
