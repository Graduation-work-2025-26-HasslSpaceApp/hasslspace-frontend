package ru.hse.app.androidApp.data.centrifugo

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.centrifugal.centrifuge.Client
import io.github.centrifugal.centrifuge.ConnectedEvent
import io.github.centrifugal.centrifuge.ConnectingEvent
import io.github.centrifugal.centrifuge.DisconnectedEvent
import io.github.centrifugal.centrifuge.ErrorEvent
import io.github.centrifugal.centrifuge.EventListener
import io.github.centrifugal.centrifuge.Options
import io.github.centrifugal.centrifuge.PublicationEvent
import io.github.centrifugal.centrifuge.Subscription
import io.github.centrifugal.centrifuge.SubscriptionEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.data.model.MessageDto
import ru.hse.app.androidApp.domain.model.entity.toDomain
import ru.hse.app.androidApp.domain.usecase.chats.GetCentrifugoTokenUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CentrifugeServiceImpl @Inject constructor(
    private val getCentrifugoTokenUseCase: GetCentrifugoTokenUseCase,
    private val mapper: ObjectMapper,
) : CentrifugeService {

    private val _incomingMessages = MutableSharedFlow<IncomingMessage>()
    override val incomingMessages: SharedFlow<IncomingMessage> = _incomingMessages

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    override val connectionState: StateFlow<ConnectionState> = _connectionState

    private var client: Client? = null
    private val subscriptions = mutableMapOf<String, Subscription>()
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun connect(url: String) {
        if (client != null && _connectionState.value == ConnectionState.CONNECTED) {
            Log.d("CentrifugeServiceImpl", "Already connected, skipping")
            return
        }

        scope.launch {
            _connectionState.value = ConnectionState.CONNECTING

            val connectionTokenResult = runCatching {
                getCentrifugoTokenUseCase()
            }.getOrElse { e ->
                Log.e("CentrifugeServiceImpl", "Failed to get Centrifugo token", e)
                _connectionState.value = ConnectionState.ERROR
                return@launch
            }

            val listener = object : EventListener() {
                override fun onConnected(client: Client, event: ConnectedEvent) {
                    Log.d("CentrifugeServiceImpl", "Connected")
                    _connectionState.value = ConnectionState.CONNECTED
                }

                override fun onConnecting(client: Client, event: ConnectingEvent) {
                    Log.d("CentrifugeServiceImpl", "Connecting...")
                    _connectionState.value = ConnectionState.CONNECTING
                }

                override fun onDisconnected(client: Client, event: DisconnectedEvent) {
                    Log.d("CentrifugeServiceImpl", "Disconnected: ${event.reason}")
                    _connectionState.value = ConnectionState.DISCONNECTED
                }

                override fun onError(client: Client, event: ErrorEvent) {
                    Log.e("CentrifugeServiceImpl", "Centrifuge error: ${event.error}")
                    _connectionState.value = ConnectionState.ERROR
                }
            }

            connectionTokenResult.fold(
                onSuccess = { token ->
                    val options = Options().apply {
                        this.token = token
                    }

                    try {
                        client?.disconnect()
                        client = Client(url, options, listener)
                        client?.connect()
                    } catch (e: Exception) {
                        Log.e("CentrifugeServiceImpl", "Failed to connect to Centrifugo", e)
                        _connectionState.value = ConnectionState.ERROR
                        client = null
                    }
                },
                onFailure = {
                    Log.e("CentrifugeServiceImpl", "Failed to get Centrifugo token. Getting token failed")
                    _connectionState.value = ConnectionState.ERROR
                    return@launch
                }
            )
        }
    }

    override fun subscribeToChannel(channel: String) {
        val chatChannelId = "chat:$channel"

        if (subscriptions.containsKey(chatChannelId)) {
            Log.d("CentrifugeServiceImpl", "Already subscribed to channel: $chatChannelId")
            return
        }

        val client = client ?: return

        val subListener = object : SubscriptionEventListener() {
            override fun onPublication(sub: Subscription, event: PublicationEvent) {
                try {
                    val json = String(event.data)
                    val dto: MessageDto = mapper.readValue(json, MessageDto::class.java)

                    scope.launch {
                        val cleanedChannelName = sub.channel.replace("chat:", "")
                        _incomingMessages.emit(IncomingMessage(channel = cleanedChannelName, data = dto.toDomain()))
                    }
                } catch (e: Exception) {
                    Log.e("CentrifugeServiceImpl", "Error parsing JSON", e)
                }
            }
        }

        val sub = client.newSubscription(chatChannelId, subListener)
        subscriptions[chatChannelId] = sub
        sub.subscribe()

        Log.d("CentrifugeServiceImpl", "Subscribed to channel: $chatChannelId")
    }

    override fun subscribeToChannels(channels: List<String>) {
        channels.forEach { channel ->
            subscribeToChannel(channel)
        }
    }

    override fun unsubscribeFromChannel(channel: String) {
        val chatChannelId = "chat:$channel"

        subscriptions[chatChannelId]?.unsubscribe()
        subscriptions.remove(chatChannelId)
    }

    override fun unsubscribeFromChannels(channels: List<String>) {
        channels.forEach { channel ->
            unsubscribeFromChannel(channel)
        }
    }

    override fun disconnect() {
        subscriptions.values.forEach { it.unsubscribe() }
        subscriptions.clear()
        client?.disconnect()
        client = null
    }
}