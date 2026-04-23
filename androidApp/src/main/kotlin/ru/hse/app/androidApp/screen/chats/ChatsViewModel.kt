package ru.hse.app.androidApp.screen.chats

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import ru.hse.app.androidApp.domain.usecase.friends.GetUserFriendsUseCase
import ru.hse.app.androidApp.domain.usecase.profile.LoadUserInfoUseCase
import ru.hse.app.androidApp.ui.entity.model.ChatShortUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.ChatsUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.ChatsUiState
import ru.hse.app.androidApp.ui.entity.model.chats.events.StartChatEvent
import ru.hse.app.androidApp.ui.entity.model.chats.toChatShort
import ru.hse.app.androidApp.ui.entity.model.chats.toMessageShortUi
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadUserFriendsEvent
import ru.hse.app.androidApp.ui.entity.model.toUi
import ru.hse.app.androidApp.ui.errorhandling.ErrorHandler
import ru.hse.coursework.godaily.ui.notification.ToastManager
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val getPrivateChatsUseCase: GetPrivateChatsUseCase,
    private val observeAllUnreadCountsUseCase: ObserveAllUnreadCountsUseCase,
    private val observeUnreadCountUseCase: ObserveUnreadCountUseCase,
    private val saveMessageToRoomUseCase: SaveMessageToRoomUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val startChatUseCase: StartChatUseCase,

    private val markMessageAsReadUseCase: MarkMessageAsReadUseCase,
    private val markChatAsReadUseCase: MarkChatAsReadUseCase,
    private val updateChatMessagesRestUseCase: UpdateChatMessagesRestUseCase,

    private val loadUserInfoUseCase: LoadUserInfoUseCase,

    private val centrifugeService: CentrifugeService,

    private val dataManager: DataManager,
    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val errorHandler: ErrorHandler,

    val searchChatsUseCase: SearchChatsUseCase,
    private val getUserFriendsUseCase: GetUserFriendsUseCase,
    val imageLoader: ImageLoader
) : ViewModel() {

    val isDark = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ChatsUiState>(ChatsUiState.Loading)
    val uiState: StateFlow<ChatsUiState> = _uiState

    private val _loadUserFriendsEvent = MutableStateFlow<LoadUserFriendsEvent?>(null)
    val loadUserFriendsEvent: StateFlow<LoadUserFriendsEvent?> = _loadUserFriendsEvent

    private val _startChatEvent = MutableStateFlow<StartChatEvent?>(null)
    val startChatEvent: StateFlow<StartChatEvent?> = _startChatEvent

    val originalChats = mutableStateListOf<ChatShortUiModel>()

    val searchText = mutableStateOf("")

    val searchTextFriends = mutableStateOf("")

    init {
        loadChats()
    }

    fun onSearchFriendsValueChange(value: String) {
        searchTextFriends.value = value
    }

    fun loadUserFriends() {
        viewModelScope.launch {
            val result = getUserFriendsUseCase()

            _loadUserFriendsEvent.value = result.fold(
                onSuccess = { friends ->
                    val currentState = _uiState.value
                    if (currentState is ChatsUiState.Success) {
                        val updatedData = currentState.data.copy(
                            friends = friends.map { it.toUi() }
                        )
                        _uiState.value = ChatsUiState.Success(updatedData)
                    }
                    LoadUserFriendsEvent.SuccessLoad
                },
                onFailure = {
                    LoadUserFriendsEvent.Error(
                        ("Ошибка при загрузке друзей. " + it.message)
                    )
                }
            )
        }
    }

    fun loadChats() {
        viewModelScope.launch {
            val curUserResult = loadUserInfoUseCase()

            curUserResult.fold(
                onSuccess = { user ->
                    val result = getPrivateChatsUseCase(user.id)
                    _uiState.value = result.fold(
                        onSuccess = { chats ->
                            val chatModels = chats.map { it.toChatShort() }
                                .sortedByDescending { chat ->
                                    chat.messages.maxByOrNull { it.timestamp }?.timestamp ?: LocalDateTime.MIN
                                }
                            chatModels.forEach { observeMessagesAndUnreadCount(it) }

                            connectToCentrifugo(chatModels.map { it.id })

                            originalChats.clear()
                            originalChats.addAll(chatModels)

                            ChatsUiState.Success(
                                data = ChatsUiModel(chats = chatModels, friends = emptyList())
                            )
                        },
                        onFailure = {
                            ChatsUiState.Error("Ошибка при загрузке чатов: ${it.message}")
                        }
                    )

                    launch {
                        loadUserFriends()
                    }
                },
                onFailure = {
                    ChatsUiState.Error("Ошибка при загрузке чатов: ${it.message}")
                }
            )
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            val curState = _uiState.value
            if (curState is ChatsUiState.Success) {
                val chats = curState.data.chats

                for (chat in chats) {
                    updateChatMessagesRestUseCase(chat.id)
                }
            }
        }
    }

    private fun observeMessagesAndUnreadCount(chat: ChatShortUiModel) {
        viewModelScope.launch {
            getChatMessagesUseCase.observeMessages(chat.id)
                .collect { messages ->
                    val updatedMessages = messages.map { it.toMessageShortUi() }
                    val unreadCount = messages.count { !it.isRead }

                    val updatedChat = chat.copy(
                        messages = updatedMessages,
                        unreadCount = unreadCount
                    )
                    updateChatState(updatedChat)
                }
        }
    }

    private fun updateChatState(updatedChat: ChatShortUiModel) {
        val currentState = _uiState.value
        if (currentState is ChatsUiState.Success) {
            val updatedChats = currentState.data.chats.toMutableList()
            val chatIndex = updatedChats.indexOfFirst { it.id == updatedChat.id }
            if (chatIndex != -1) {
                updatedChats[chatIndex] = updatedChat
            } else {
                updatedChats.add(updatedChat)
            }

            val sortedChats = updatedChats.sortedByDescending { chat ->
                chat.messages.maxByOrNull { it.timestamp }?.timestamp ?: LocalDateTime.MIN
            }

            originalChats.clear()
            originalChats.addAll(sortedChats)

            _uiState.value = ChatsUiState.Success(
                data = currentState.data.copy(chats = sortedChats)
            )
        }
    }

    fun onMessageClick(userId: String) {
        viewModelScope.launch {
            val result = startChatUseCase(userId)

            _startChatEvent.value = result.fold(
                onSuccess = { chatId ->
                    StartChatEvent.Success(chatId)
                },
                onFailure = {
                    StartChatEvent.Error("Ошибка при создании чата. " + it.message)
                }
            )
        }
    }

    fun connectToCentrifugo(chatIds: List<String>) {
        centrifugeService.connect(BuildConfig.CENTRIFUGO_URL)
        centrifugeService.subscribeToChannels(chatIds)
    }


    fun onSearchValueChange(value: String) {
        searchText.value = value

        val currentState = _uiState.value
        if (currentState is ChatsUiState.Success) {
            viewModelScope.launch {

                val result = searchChatsUseCase(originalChats.map { it }, value)
                val updatedData = currentState.data.copy(
                    chats = result.map { it }.sortedByDescending { chat ->
                        chat.messages.maxByOrNull { it.timestamp }?.timestamp ?: LocalDateTime.MIN
                    }
                )
                _uiState.value = ChatsUiState.Success(updatedData)
            }
        }
    }

    fun handleError(message: String) {
        errorHandler.handleError(
            message = message
        )
    }

    fun resetLoadUserFriendsEvent() {
        _loadUserFriendsEvent.value = null
    }

    fun resetStartChatEvent() {
        _startChatEvent.value = null
    }
}