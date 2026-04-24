package ru.hse.app.hasslspace.ui.entity.model.servers.events

sealed class StartChatChannelEvent {
    data class Success(val serverId: String, val channelId: String, val chatId: String, val currestUserId: String) : StartChatChannelEvent()
    data class Error(val message: String) : StartChatChannelEvent()
}
