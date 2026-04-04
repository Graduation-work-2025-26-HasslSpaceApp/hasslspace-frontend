package ru.hse.app.androidApp.ui.entity.model.call.events

sealed class GetTokenEvent {
    data class Success(val token: String, val roomName: String, val videoEnabled: Boolean = false) :
        GetTokenEvent()

    data class Error(val message: String) : GetTokenEvent()
}
