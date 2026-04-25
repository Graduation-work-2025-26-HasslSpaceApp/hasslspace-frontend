package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class LoadChosenUserEvent {
    data object SuccessLoad : LoadChosenUserEvent()
    data class Error(val message: String) : LoadChosenUserEvent()
}
