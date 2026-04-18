package ru.hse.app.androidApp.data.centrifugo

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import ru.hse.app.androidApp.domain.model.entity.Message

interface CentrifugeService {
    val incomingMessages: SharedFlow<IncomingMessage>
    val connectionState: StateFlow<ConnectionState>

    fun connect(url: String)
    fun disconnect()
    fun subscribeToChannel(channel: String)
    fun unsubscribeFromChannel(channel: String)
    fun subscribeToChannels(channels: List<String>)
    fun unsubscribeFromChannels(channels: List<String>)
}

data class IncomingMessage(
    val channel: String,
    val data: Message,
)

enum class ConnectionState {
    CONNECTING, CONNECTED, DISCONNECTED, ERROR
}