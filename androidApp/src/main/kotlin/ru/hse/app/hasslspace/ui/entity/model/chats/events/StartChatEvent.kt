package ru.hse.app.hasslspace.ui.entity.model.chats.events

sealed class StartChatEvent {
    data class Success(val chatId: String) : StartChatEvent()
    data class Error(val message: String, val exception: Throwable) : StartChatEvent()
}
