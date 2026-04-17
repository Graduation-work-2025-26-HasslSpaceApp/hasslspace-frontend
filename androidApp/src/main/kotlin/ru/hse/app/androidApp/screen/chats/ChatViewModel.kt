package ru.hse.app.androidApp.screen.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.BuildConfig
import ru.hse.app.androidApp.data.centrifugo.CentrifugeService
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.usecase.chats.GetChatMessagesUseCase
import ru.hse.app.androidApp.domain.usecase.chats.GetPrivateChatsUseCase
import ru.hse.app.androidApp.domain.usecase.chats.MarkChatAsReadUseCase
import ru.hse.app.androidApp.domain.usecase.chats.MarkMessageAsReadUseCase
import ru.hse.app.androidApp.domain.usecase.chats.ObserveAllUnreadCountsUseCase
import ru.hse.app.androidApp.domain.usecase.chats.ObserveUnreadCountUseCase
import ru.hse.app.androidApp.domain.usecase.chats.SaveMessageToRoomUseCase
import ru.hse.app.androidApp.domain.usecase.chats.SearchChatsUseCase
import ru.hse.app.androidApp.domain.usecase.chats.SendMessageUseCase
import ru.hse.app.androidApp.domain.usecase.chats.StartChatUseCase
import ru.hse.app.androidApp.domain.usecase.chats.UpdateChatMessagesRestUseCase
import ru.hse.app.androidApp.domain.usecase.profile.LoadUserInfoUseCase
import ru.hse.app.androidApp.domain.usecase.servers.JoinServerUseCase
import ru.hse.app.androidApp.ui.entity.model.chats.ChatUiState
import ru.hse.app.androidApp.ui.entity.model.chats.MessageUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.events.GetPrivateChatMessagesEvent
import ru.hse.app.androidApp.ui.entity.model.chats.events.GetPrivateChatsEvent
import ru.hse.app.androidApp.ui.entity.model.chats.events.SendMessageEvent
import ru.hse.app.androidApp.ui.entity.model.chats.toUi
import ru.hse.app.androidApp.ui.entity.model.chats.toUiPrivate
import ru.hse.app.androidApp.ui.entity.model.servers.events.JoinServerEvent
import ru.hse.app.androidApp.ui.errorhandling.ErrorHandler
import ru.hse.coursework.godaily.ui.notification.ToastManager
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
    private val updateChatMessagesRestUseCase: UpdateChatMessagesRestUseCase,

    private val markMessageAsReadUseCase: MarkMessageAsReadUseCase,
    private val markChatAsReadUseCase: MarkChatAsReadUseCase,

    private val loadUserInfoUseCase: LoadUserInfoUseCase,

    private val joinServerUseCase: JoinServerUseCase,

    private val dataManager: DataManager,
    private val toastManager: ToastManager,
    private val errorHandler: ErrorHandler,

    val cropProfilePhotoService: CropProfilePhotoService,
    private val centrifugeService: CentrifugeService,
    val imageLoader: ImageLoader
) : ViewModel() {
    val isDark = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState

    private val messagesCache = mutableMapOf<String, MessageUiModel>()

    private val _getPrivateChatMessagesEvent = MutableStateFlow<GetPrivateChatMessagesEvent?>(null)
    val getPrivateChatMessagesEvent: StateFlow<GetPrivateChatMessagesEvent?> =
        _getPrivateChatMessagesEvent

    private val _getPrivateChatsEvent = MutableStateFlow<GetPrivateChatsEvent?>(null)
    val getPrivateChatsEvent: StateFlow<GetPrivateChatsEvent?> = _getPrivateChatsEvent

    private val _sendMessageEvent = MutableStateFlow<SendMessageEvent?>(null)
    val sendMessageEvent: StateFlow<SendMessageEvent?> = _sendMessageEvent

    private val _joinServerEvent = MutableStateFlow<JoinServerEvent?>(null)
    val joinServerEvent: StateFlow<JoinServerEvent?> = _joinServerEvent

    fun loadChatInitInfo(chatId: String) {
        viewModelScope.launch {
            val curUserResult = loadUserInfoUseCase()

            _getPrivateChatsEvent.value = curUserResult.fold(
                onSuccess = { curUser ->
                    val result = getPrivateChatsUseCase(curUser.id)

                    result.fold(
                        onSuccess = { chats ->
                            val currentChat = chats.find { it.id == chatId }
                            if (currentChat != null) {
                                val chatUi = currentChat.toUiPrivate()

                                _uiState.value = ChatUiState.Success(
                                    data = chatUi
                                )

                                viewModelScope.launch {
                                    getChatMessagesUseCase.observeMessages(chatId)
                                        .collect { messages ->
                                            val curState = _uiState.value
                                            if (curState is ChatUiState.Success) {
                                                val uiMessages = messages.map { message ->
                                                    val cachedMessage = messagesCache[message.id]
                                                    if (cachedMessage != null && cachedMessage.isRead != message.isRead) {
                                                        messagesCache[message.id] =
                                                            message.toUi(curState.data.channelMembers)
                                                    }

                                                    messagesCache.getOrPut(message.id) {
                                                        message.toUi(curState.data.channelMembers)
                                                    }
                                                }

                                                connectToCentrifugo(chatId)

                                                _uiState.value = ChatUiState.Success(
                                                    data = curState.data.copy(messages = uiMessages)
                                                )
                                            }
                                        }
                                }

                            } else {
                                GetPrivateChatsEvent.Error("Ошибка при загрузке выбранного чата. ")
                            }
                            GetPrivateChatsEvent.SuccessLoad
                        },
                        onFailure = {
                            GetPrivateChatsEvent.Error("Ошибка при загрузке чатов. " + it.message)
                        }
                    )
                },
                onFailure = {
                    GetPrivateChatsEvent.Error("Ошибка при загрузке текущего пользователя. " + it.message)
                }
            )
        }
    }

    fun refreshMessages(chatId: String) {
        viewModelScope.launch {
            updateChatMessagesRestUseCase(chatId)
        }
    }

    fun connectToCentrifugo(chatId: String) {
        centrifugeService.connect(BuildConfig.CENTRIFUGO_URL)
        centrifugeService.subscribeToChannel(chatId)
    }

    fun markMessageAsRead(messageId: String) {
        viewModelScope.launch {
            markMessageAsReadUseCase.invoke(messageId)
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

    fun joinServer(code: String) {
        when {
            code.isBlank() -> {
                errorHandler.handleError("Код не может быть пустым")
                return
            }
            else -> {
                viewModelScope.launch {
                    val result =
                        joinServerUseCase(code)

                    _joinServerEvent.value = result.fold(
                        onSuccess = {
                            JoinServerEvent.Success
                        },
                        onFailure = { error ->
                            JoinServerEvent.Error("Ошибка при присоединении к серверу. ${error.message}")
                        }
                    )
                }
            }
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

    fun resetJoinServerEvent() {
        _joinServerEvent.value = null
    }
}