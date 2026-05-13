package ru.hse.app.hasslspace.ui.entity.model.profile.events

sealed class LoadChosenUserCommonServersEvent {
    data object SuccessLoad : LoadChosenUserCommonServersEvent()
    data class Error(val message: String, val exception: Throwable) :
        LoadChosenUserCommonServersEvent()
}
