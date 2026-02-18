package ru.hse.app.androidApp.screen.profile

import android.net.Uri
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
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService
import ru.hse.app.androidApp.domain.usecase.profile.ChangeUserDescUseCase
import ru.hse.app.androidApp.domain.usecase.profile.ChangeUserNameUseCase
import ru.hse.app.androidApp.domain.usecase.profile.ChangeUserStatusUseCase
import ru.hse.app.androidApp.domain.usecase.profile.GetUserFriendsUseCase
import ru.hse.app.androidApp.domain.usecase.profile.GetUserServersUseCase
import ru.hse.app.androidApp.domain.usecase.profile.LoadUserInfoUseCase
import ru.hse.app.androidApp.domain.usecase.profile.SaveUserPhotoUseCase
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.entity.model.auth.SavePhotoEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserDataEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserFriendsEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserServersEvent
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState
import ru.hse.app.androidApp.ui.entity.model.profile.SaveUserDescEvent
import ru.hse.app.androidApp.ui.entity.model.profile.SaveUserNameEvent
import ru.hse.app.androidApp.ui.entity.model.profile.SaveUserStatusEvent
import ru.hse.app.androidApp.ui.entity.model.profile.toUI
import ru.hse.app.androidApp.ui.entity.model.toDomain
import ru.hse.app.androidApp.ui.entity.model.toUi
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getUserFriendsUseCase: GetUserFriendsUseCase,
    private val getUserServersUseCase: GetUserServersUseCase,
    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
    private val changeUserNameUseCase: ChangeUserNameUseCase,
    private val changeUserStatusUseCase: ChangeUserStatusUseCase,
    private val changeUserDescUseCase: ChangeUserDescUseCase,

    private val photoConverterService: PhotoConverterService,
    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val imageLoader: ImageLoader
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _loadUserInfoEvent = MutableStateFlow<LoadUserDataEvent?>(null)
    val loadUserInfoEvent: StateFlow<LoadUserDataEvent?> = _loadUserInfoEvent

    private val _loadUserFriendsEvent = MutableStateFlow<LoadUserFriendsEvent?>(null)
    val loadUserFriendsEvent: StateFlow<LoadUserFriendsEvent?> = _loadUserFriendsEvent

    private val _loadUserServersEvent = MutableStateFlow<LoadUserServersEvent?>(null)
    val loadUserServersEvent: StateFlow<LoadUserServersEvent?> = _loadUserServersEvent

    private val _savePhotoEvent = MutableStateFlow<SavePhotoEvent?>(null)
    val savePhotoEvent: StateFlow<SavePhotoEvent?> = _savePhotoEvent

    private val _saveUserNameEvent = MutableStateFlow<SaveUserNameEvent?>(null)
    val saveUserNameEvent: StateFlow<SaveUserNameEvent?> = _saveUserNameEvent

    private val _saveUserStatusEvent = MutableStateFlow<SaveUserStatusEvent?>(null)
    val saveUserStatusEvent: StateFlow<SaveUserStatusEvent?> = _saveUserStatusEvent

    private val _saveUserDescEvent = MutableStateFlow<SaveUserDescEvent?>(null)
    val saveUserDescEvent: StateFlow<SaveUserDescEvent?> = _saveUserDescEvent

    val originalUsername = mutableStateOf("")
    val isUsernameMatched = mutableStateOf(false)
    val showStatusSheet = mutableStateOf(false)

    init {
        loadUserData()
        loadUserFriends()
        loadUserServers()
        loadAppTheme()

        viewModelScope.launch {
            dataManager.isDark.collect { isDarkTheme ->
                val currentState = _uiState.value
                if (currentState is ProfileUiState.Success) {
                    val updatedData = currentState.data.copy(
                        isDarkCheck = isDarkTheme
                    )
                    originalUsername.value = updatedData.username
                    isUsernameMatched.value = true
                    _uiState.value = ProfileUiState.Success(updatedData)
                }
            }
        }
    }

    fun loadUserData() {
        viewModelScope.launch {
            val result = loadUserInfoUseCase()

            _loadUserInfoEvent.value = result.fold(
                onSuccess = { info ->
                    _uiState.value = ProfileUiState.Success(
                        data = (info.toUI()).copy(
                            selectedImageUri = urlToUri(info.avatarUrl)
                        )
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

    fun getStatusOptions(): List<StatusPresentation> {
        return listOf(
            StatusPresentation.ACTIVE,
            StatusPresentation.NOT_ACTIVE,
            StatusPresentation.INVISIBLE,
            StatusPresentation.DO_NOT_DISTURB
        )
    }

    fun urlToUri(url: String?): Uri? {
        return photoConverterService.urlToUri(url)
    }

    fun loadAppTheme() {
        val currentStateIsDark = dataManager.isDark.value
        onIsDarkChanged(currentStateIsDark)
    }

    fun saveThemeToStorage(isDark: Boolean) {
        viewModelScope.launch {
            dataManager.saveIsDarkTheme(isDark)
        }
    }

    fun onIsDarkChanged(isDark: Boolean) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            saveThemeToStorage(isDark)
            val updatedData = currentState.data.copy(isDarkCheck = isDark)
            _uiState.value = ProfileUiState.Success(updatedData)
        }
    }

    fun onSelectedImageUri(uri: Uri?) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val updatedData = currentState.data.copy(selectedImageUri = uri)
            _uiState.value = ProfileUiState.Success(updatedData)
            saveUserPhoto(uri) //todo update ссылки на фото
        }
    }

    fun onUsernameChanged(username: String) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val updatedData = currentState.data.copy(username = username)
            isUsernameMatched.value = (originalUsername.value == username)
            _uiState.value = ProfileUiState.Success(updatedData)
        }
    }

    fun onSelectedStatusChanged(status: StatusPresentation) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val updatedData = currentState.data.copy(status = status)
            _uiState.value =
                ProfileUiState.Success(updatedData) //TODO будет меняться даже при несохранении, подумать об этом
        }
    }

    fun onApplyNewUsername() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val data = currentState.data
            viewModelScope.launch {
                val result = changeUserNameUseCase(data.username)

                _saveUserNameEvent.value = result.fold(
                    onSuccess = {
                        SaveUserNameEvent.SuccessSave
                    },
                    onFailure = {
                        SaveUserNameEvent.Error("Ошибка при сохранении нового имени. " + it.message)
                    }
                )
            }
        }
    }

    fun onDescChanged(description: String) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val updatedData = currentState.data.copy(description = description)
            _uiState.value = ProfileUiState.Success(updatedData)
        }
    }

    fun onApplyDescClick() {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val data = currentState.data
            viewModelScope.launch {
                val result = changeUserDescUseCase(data.description)

                _saveUserDescEvent.value = result.fold(
                    onSuccess = {
                        SaveUserDescEvent.SuccessSave
                    },
                    onFailure = {
                        SaveUserDescEvent.Error("Ошибка при сохранении описания. " + it.message)
                    }
                )
            }
        }
    }

    fun saveUserPhoto(uriValue: Uri?) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val data = currentState.data
            viewModelScope.launch {
                uriValue?.let { uri ->
                    val photoResultResponse =
                        saveUserPhotoUseCase(uri, data.profilePictureUrl.takeIf { it.isNotEmpty() })
                    _savePhotoEvent.value = photoResultResponse.fold(
                        onSuccess = { _ ->
                            SavePhotoEvent.SuccessSave
                        },
                        onFailure = {
                            SavePhotoEvent.Error(it.message ?: "")
                        }
                    )
                }
            }
        }
    }

    fun onApplyStatus(status: StatusPresentation) {
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            val data = currentState.data
            viewModelScope.launch {
                val result = changeUserStatusUseCase(status.toDomain())

                _saveUserStatusEvent.value = result.fold(
                    onSuccess = {
                        SaveUserStatusEvent.SuccessSave
                    },
                    onFailure = {
                        SaveUserStatusEvent.Error("Ошибка при сохранении статуса. " + it.message)
                    }
                )
            }
        }
    }

    fun exit() {
        dataManager.clearJwt()
        dataManager.clearVerificationStatus()
    }
}