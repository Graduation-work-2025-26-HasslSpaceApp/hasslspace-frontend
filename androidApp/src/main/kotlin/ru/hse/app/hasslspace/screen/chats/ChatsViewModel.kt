package ru.hse.app.hasslspace.screen.chats

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.hse.app.hasslspace.BuildConfig
import ru.hse.app.hasslspace.data.centrifugo.CentrifugeService
import ru.hse.app.hasslspace.data.local.DataManager
import ru.hse.app.hasslspace.data.roomstorage.MessageEntity
import ru.hse.app.hasslspace.domain.service.common.CropProfilePhotoService
import ru.hse.app.hasslspace.domain.usecase.chats.GetChatMessagesUseCase
import ru.hse.app.hasslspace.domain.usecase.chats.GetPrivateChatsUseCase
import ru.hse.app.hasslspace.domain.usecase.chats.SearchChatsUseCase
import ru.hse.app.hasslspace.domain.usecase.chats.StartChatUseCase
import ru.hse.app.hasslspace.domain.usecase.chats.UpdateChatMessagesRestUseCase
import ru.hse.app.hasslspace.domain.usecase.friends.GetUserFriendsUseCase
import ru.hse.app.hasslspace.domain.usecase.profile.LoadUserInfoUseCase
import ru.hse.app.hasslspace.ui.entity.model.ChatShortUiModel
import ru.hse.app.hasslspace.ui.entity.model.chats.ChatsUiModel
import ru.hse.app.hasslspace.ui.entity.model.chats.ChatsUiState
import ru.hse.app.hasslspace.ui.entity.model.chats.events.StartChatEvent
import ru.hse.app.hasslspace.ui.entity.model.chats.toChatShort
import ru.hse.app.hasslspace.ui.entity.model.chats.toMessageShortUi
import ru.hse.app.hasslspace.ui.entity.model.profile.events.LoadUserFriendsEvent
import ru.hse.app.hasslspace.ui.entity.model.toUi
import ru.hse.app.hasslspace.ui.errorhandling.ErrorHandler
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val getPrivateChatsUseCase: GetPrivateChatsUseCase,
    private val startChatUseCase: StartChatUseCase,
    private val updateChatMessagesRestUseCase: UpdateChatMessagesRestUseCase,

    private val loadUserInfoUseCase: LoadUserInfoUseCase,

    private val centrifugeService: CentrifugeService,

    private val dataManager: DataManager,
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
                        ("Ошибка при загрузке друзей. " + it.message), it
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
                                    chat.messages.maxByOrNull { it.timestamp }?.timestamp
                                        ?: LocalDateTime.MIN
                                }
                            observeAllChats(chatModels)

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

    @OptIn(FlowPreview::class)
    private fun observeAllChats(chats: List<ChatShortUiModel>) {
        if (chats.isEmpty()) return
        viewModelScope.launch {
            val flows = chats.map { chat ->
                getChatMessagesUseCase.observeMessages(chat.id)
                    .map { msgs -> chat.id to msgs }
            }
            combine(flows) { pairs -> pairs.toMap() }
                .debounce(50)
                .distinctUntilChanged()
                .collect { messagesByChatId ->
                    rebuildChats(messagesByChatId)
                }
        }
    }

    private fun rebuildChats(messagesByChatId: Map<String, List<MessageEntity>>) {
        val current = _uiState.value as? ChatsUiState.Success ?: return
        val updated = current.data.chats.map { chat ->
            val msgs = messagesByChatId[chat.id].orEmpty()
            chat.copy(
                messages = msgs.map { it.toMessageShortUi() },
                unreadCount = msgs.count { !it.isRead }
            )
        }.sortedByDescending { c ->
            c.messages.maxByOrNull { it.timestamp }?.timestamp ?: LocalDateTime.MIN
        }
        originalChats.clear()
        originalChats.addAll(updated)
        _uiState.value = ChatsUiState.Success(current.data.copy(chats = updated))
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
                    StartChatEvent.Error("Ошибка при создании чата. " + it.message, it)
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

    fun handleError(message: String, exception: Exception) {
        errorHandler.handleError(
            message = message,
            exception = exception
        )
    }

    fun handleInfo(message: String) {
        errorHandler.handleInfo(message = message)
    }

    fun resetLoadUserFriendsEvent() {
        _loadUserFriendsEvent.value = null
    }

    fun resetStartChatEvent() {
        _startChatEvent.value = null
    }
}