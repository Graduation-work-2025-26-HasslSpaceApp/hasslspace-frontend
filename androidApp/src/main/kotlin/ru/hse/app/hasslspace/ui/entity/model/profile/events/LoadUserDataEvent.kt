package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class LoadUserDataEvent {
    data object SuccessLoad : LoadUserDataEvent()
    data class Error(val message: String, val exception: Throwable) : LoadUserDataEvent()
}
