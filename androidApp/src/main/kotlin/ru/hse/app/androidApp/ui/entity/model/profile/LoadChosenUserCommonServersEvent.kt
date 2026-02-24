package ru.hse.app.androidApp.ui.entity.model.profile

sealed class LoadChosenUserCommonServersEvent {
    data object SuccessLoad : LoadChosenUserCommonServersEvent()
    data class Error(val message: String) : LoadChosenUserCommonServersEvent()
}
