package ru.hse.app.androidApp.ui.entity.model.profile.events

sealed class LoadUserServersEvent {
    data object SuccessLoad : LoadUserServersEvent()
    data class Error(val message: String) : LoadUserServersEvent()
}
