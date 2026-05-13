package ru.hse.app.hasslspace.ui.entity.model.chats.events

sealed class SendMessageEvent {
    data object Success : SendMessageEvent()
    data class Error(val message: String, val exception: Throwable) : SendMessageEvent()
}
