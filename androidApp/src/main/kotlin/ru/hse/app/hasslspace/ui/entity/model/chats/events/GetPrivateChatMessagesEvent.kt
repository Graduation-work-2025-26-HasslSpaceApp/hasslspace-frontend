package ru.hse.app.hasslspace.ui.entity.model.chats.events

sealed class GetPrivateChatMessagesEvent {
    data object SuccessLoad : GetPrivateChatMessagesEvent()
    data class Error(val message: String) : GetPrivateChatMessagesEvent()
}
