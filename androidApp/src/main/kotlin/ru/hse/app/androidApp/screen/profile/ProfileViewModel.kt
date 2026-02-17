package ru.hse.app.androidApp.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.usecase.profile.GetUserFriendsUseCase
import ru.hse.app.androidApp.domain.usecase.profile.GetUserServersUseCase
import ru.hse.app.androidApp.domain.usecase.profile.LoadUserInfoUseCase
import ru.hse.app.androidApp.ui.components.profile.servers.servers
import ru.hse.app.androidApp.ui.entity.model.auth.AuthUiState
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserDataEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserFriendsEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserServersEvent
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState
import ru.hse.app.androidApp.ui.entity.model.profile.toUI
import ru.hse.app.androidApp.ui.entity.model.toUi
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getUserFriendsUseCase: GetUserFriendsUseCase,
    private val getUserServersUseCase: GetUserServersUseCase,
    private val toastManager: ToastManager,
    val imageLoader: ImageLoader
) : ViewModel() {
    val isDarkTheme = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _loadUserInfoEvent = MutableStateFlow<LoadUserDataEvent?>(null)
    val loadUserInfoEvent: StateFlow<LoadUserDataEvent?> = _loadUserInfoEvent

    private val _loadUserFriendsEvent = MutableStateFlow<LoadUserFriendsEvent?>(null)
    val loadUserFriendsEvent: StateFlow<LoadUserFriendsEvent?> = _loadUserFriendsEvent

    private val _loadUserServersEvent = MutableStateFlow<LoadUserServersEvent?>(null)
    val loadUserServersEvent: StateFlow<LoadUserServersEvent?> = _loadUserServersEvent

    init {
        loadUserData()
        loadUserFriends()
        loadUserServers()
    }

    fun loadUserData() {
        viewModelScope.launch {
            val result = loadUserInfoUseCase()

            _loadUserInfoEvent.value = result.fold(
                onSuccess = { info ->
                    _uiState.value = ProfileUiState.Success(
                        data = info.toUI()
                    )
                    LoadUserDataEvent.SuccessLoad
                },
                onFailure = {
                    LoadUserDataEvent.Error(
                        ("Ошибка при загрузке профиля. " + it.message)
                    )
                }
            )
        }
    }

    fun loadUserFriends() {
        viewModelScope.launch {
            val result = getUserFriendsUseCase()

            _loadUserFriendsEvent.value = result.fold(
                onSuccess = { friends ->
                    val currentState = _uiState.value
                    if (currentState is ProfileUiState.Success) {
                        val updatedData = currentState.data.copy(
                            friends = friends.map { it.toUi() }
                        )
                        _uiState.value = ProfileUiState.Success(updatedData)
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

    fun loadUserServers() {
        viewModelScope.launch {
            val result = getUserServersUseCase()

            _loadUserServersEvent.value = result.fold(
                onSuccess = { servers ->
                    val currentState = _uiState.value
                    if (currentState is ProfileUiState.Success) {
                        val updatedData = currentState.data.copy(
                            servers = servers.map { it.toUi() }
                        )
                        _uiState.value = ProfileUiState.Success(updatedData)
                    }
                    LoadUserServersEvent.SuccessLoad
                },
                onFailure = {
                    LoadUserServersEvent.Error(
                        ("Ошибка при загрузке серверов. " + it.message)
                    )
                }
            )
        }
    }
}