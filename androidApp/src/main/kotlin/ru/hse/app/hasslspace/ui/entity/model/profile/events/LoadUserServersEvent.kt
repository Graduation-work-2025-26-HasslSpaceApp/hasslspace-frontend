package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class LoadUserServersEvent {
    data object SuccessLoad : LoadUserServersEvent()
    data class Error(val message: String, val exception: Throwable) : LoadUserServersEvent()
}
