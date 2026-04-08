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
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.usecase.chats.SearchChatsUseCase
import ru.hse.app.androidApp.domain.usecase.friends.GetUserFriendsUseCase
import ru.hse.app.androidApp.ui.entity.model.ChatShortUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.ChatsUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.ChatsUiState
import ru.hse.app.androidApp.ui.entity.model.chats.mockChats
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadUserFriendsEvent
import ru.hse.app.androidApp.ui.entity.model.toUi
import ru.hse.app.androidApp.ui.errorhandling.ErrorHandler
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
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
        //TODO загрузка чатов
        _uiState.value = ChatsUiState.Success(
            data = ChatsUiModel(
                chats = mockChats,
                friends = listOf()
            )
        )
        originalChats.clear()
        originalChats.addAll(mockChats)
    }

    fun onSearchValueChange(value: String) {
        searchText.value = value

        val currentState = _uiState.value
        if (currentState is ChatsUiState.Success) {
            viewModelScope.launch {
                val result = searchChatsUseCase(originalChats.map { it }, value)
                val updatedData = currentState.data.copy(
                    chats = result.map { it }
                )
                _uiState.value = ChatsUiState.Success(updatedData)
            }
        }
    }

    fun resetLoadUserFriendsEvent() {
        _loadUserFriendsEvent.value = null
    }
}