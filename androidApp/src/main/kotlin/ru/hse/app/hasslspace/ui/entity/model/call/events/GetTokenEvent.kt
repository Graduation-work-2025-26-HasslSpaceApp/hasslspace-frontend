package ru.hse.app.hasslspace.ui.entity.model.call.events

sealed class GetTokenEvent {
    data class Success(
        val token: String,
        val roomName: String,
        val videoEnabled: Boolean = false,
        val limit: Int = 0
    ) :
        GetTokenEvent()

    data class Error(val message: String, val exception: Throwable) : GetTokenEvent()
}
