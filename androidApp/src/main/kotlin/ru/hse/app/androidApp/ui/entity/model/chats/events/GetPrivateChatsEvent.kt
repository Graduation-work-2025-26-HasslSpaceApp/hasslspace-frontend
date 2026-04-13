package ru.hse.app.androidApp.ui.entity.model.chats.events

sealed class GetPrivateChatsEvent {
    data object SuccessLoad : GetPrivateChatsEvent()
    data class Error(val message: String) : GetPrivateChatsEvent()
}
