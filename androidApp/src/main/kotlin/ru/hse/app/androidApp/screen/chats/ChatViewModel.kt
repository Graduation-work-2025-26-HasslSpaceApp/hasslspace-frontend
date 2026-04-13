package ru.hse.app.androidApp.screen.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.usecase.chats.*
import ru.hse.app.androidApp.ui.components.chats.chat.getAlexey
import ru.hse.app.androidApp.ui.components.chats.chat.getEkaterina
import ru.hse.app.androidApp.ui.components.chats.chat.getMeChat
import ru.hse.app.androidApp.ui.components.chats.chat.getNotMeChat
import ru.hse.app.androidApp.ui.components.chats.chat.messages
import ru.hse.app.androidApp.ui.entity.model.chats.ChatUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.ChatUiState
import ru.hse.app.androidApp.ui.entity.model.chats.MessageUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.events.GetPrivateChatMessagesEvent
import ru.hse.app.androidApp.ui.entity.model.chats.events.GetPrivateChatsEvent
import ru.hse.app.androidApp.ui.entity.model.chats.events.SendMessageEvent
import ru.hse.app.androidApp.ui.entity.model.chats.toUi
import ru.hse.app.androidApp.ui.entity.model.chats.toUiPrivate
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadUserServersEvent
import ru.hse.app.androidApp.ui.errorhandling.ErrorHandler
import ru.hse.coursework.godaily.ui.notification.ToastManager
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val getPrivateChatsUseCase: GetPrivateChatsUseCase,
    private val observeAllUnreadCountsUseCase: ObserveAllUnreadCountsUseCase,
    private val observeUnreadCountUseCase: ObserveUnreadCountUseCase,
    private val saveMessageToRoomUseCase: SaveMessageToRoomUseCase,
    private val searchChatsUseCase: SearchChatsUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val startChatUseCase: StartChatUseCase,

    private val dataManager: DataManager,
    private val toastManager: ToastManager,
    private val errorHandler: ErrorHandler,

    val cropProfilePhotoService: CropProfilePhotoService,
    val imageLoader: ImageLoader
) : ViewModel() {
    val isDark = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState

    private val _getPrivateChatMessagesEvent = MutableStateFlow<GetPrivateChatMessagesEvent?>(null)
    val getPrivateChatMessagesEvent: StateFlow<GetPrivateChatMessagesEvent?> = _getPrivateChatMessagesEvent

    private val _getPrivateChatsEvent = MutableStateFlow<GetPrivateChatsEvent?>(null)
    val getPrivateChatsEvent: StateFlow<GetPrivateChatsEvent?> = _getPrivateChatsEvent

    private val _sendMessageEvent = MutableStateFlow<SendMessageEvent?>(null)
    val sendMessageEvent: StateFlow<SendMessageEvent?> = _sendMessageEvent

    fun loadChatInitInfo(chatId: String) {
        // todo загружаем изначальный стейт для чата и сообщений и подключение к центрифуге
        viewModelScope.launch {
            val result = getPrivateChatsUseCase()

            _getPrivateChatsEvent.value = result.fold(
                onSuccess = { chats ->
                    val currentChat = chats.find { it.id == chatId }
                    if (currentChat != null) {
                        val chatUi = currentChat.toUiPrivate()

                        _uiState.value = ChatUiState.Success(
                            data = chatUi
                        )

                        val resultMsg = getChatMessagesUseCase(chatId)

                        _getPrivateChatMessagesEvent.value = resultMsg.fold(
                            onSuccess = { messages ->
                                val messagesListUi = messages.map { it.toUi(chatUi.channelMembers) }

                                _uiState.value = ChatUiState.Success(
                                    data = chatUi.copy(messages = messagesListUi)
                                )

                                GetPrivateChatMessagesEvent.SuccessLoad
                            },
                            onFailure = {
                                GetPrivateChatMessagesEvent.Error("Ошибка при загрузке сообщений. " + it.message)
                            }
                        )

                    } else {
                        GetPrivateChatsEvent.Error("Ошибка при загрузке выбранного чата. ")
                    }
                    GetPrivateChatsEvent.SuccessLoad
                },
                onFailure = {
                    GetPrivateChatsEvent.Error("Ошибка при загрузке чатов. " + it.message)
                }
            )
        }
    }

    fun addCurrentUserMessage(chatId: String, content: String) {
        viewModelScope.launch {
            val result = sendMessageUseCase(
                chatId = chatId,
                content = content
            )

            _sendMessageEvent.value = result.fold(
                onSuccess = {
                    SendMessageEvent.Success
                },
                onFailure = {
                    SendMessageEvent.Error("Ошибка при отправке сообщения на сервер. " + it.message)
                }
            )

        }
    }

    fun updateChatWithMessage(msg: MessageUiModel) {
        val currentState = _uiState.value
        if (currentState is ChatUiState.Success) {
            val updatedData = currentState.data.copy(
                messages = listOf(msg) + currentState.data.messages
            )

            _uiState.value = ChatUiState.Success(data = updatedData)
        }
    }

    fun handleError(msg: String) {
        errorHandler.handleError(msg)
    }

    fun resetGetPrivateChatsEvent() {
        _getPrivateChatsEvent.value = null
    }

    fun resetGetPrivateChatMessagesEvent() {
        _getPrivateChatMessagesEvent.value = null
    }

    fun resetSendMessageEvent() {
        _sendMessageEvent.value = null
    }
}