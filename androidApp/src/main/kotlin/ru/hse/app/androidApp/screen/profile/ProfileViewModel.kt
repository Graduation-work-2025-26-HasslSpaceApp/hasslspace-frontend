package ru.hse.app.androidApp.screen.profile

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.domain.service.common.PhotoConverterService
import ru.hse.app.androidApp.domain.usecase.friends.CreateFriendRequestUseCase
import ru.hse.app.androidApp.domain.usecase.friends.DeleteFriendshipUseCase
import ru.hse.app.androidApp.domain.usecase.friends.GetChosenUserCommonServersUseCase
import ru.hse.app.androidApp.domain.usecase.friends.GetUserFriendsUseCase
import ru.hse.app.androidApp.domain.usecase.friends.GetUserInfoExtendedUseCase
import ru.hse.app.androidApp.domain.usecase.friends.RespondToFriendshipRequestUseCase
import ru.hse.app.androidApp.domain.usecase.friends.SearchFriendsUseCase
import ru.hse.app.androidApp.domain.usecase.profile.ChangeUserDescUseCase
import ru.hse.app.androidApp.domain.usecase.profile.ChangeUserNameUseCase
import ru.hse.app.androidApp.domain.usecase.profile.ChangeUserStatusUseCase
import ru.hse.app.androidApp.domain.usecase.profile.GetUserServersUseCase
import ru.hse.app.androidApp.domain.usecase.profile.LoadUserInfoUseCase
import ru.hse.app.androidApp.domain.usecase.profile.SaveUserPhotoUseCase
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.entity.model.TypeUiModel
import ru.hse.app.androidApp.ui.entity.model.auth.SavePhotoEvent
import ru.hse.app.androidApp.ui.entity.model.profile.CreateFriendRequestEvent
import ru.hse.app.androidApp.ui.entity.model.profile.DeleteFriendshipEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadChosenUserCommonServersEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadChosenUserEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserDataEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserFriendsEvent
import ru.hse.app.androidApp.ui.entity.model.profile.LoadUserServersEvent
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState
import ru.hse.app.androidApp.ui.entity.model.profile.RespondToFriendRequestEvent
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
    @ApplicationContext context: Context,
    // Profile and settings
    private val dataManager: DataManager,
    private val loadUserInfoUseCase: LoadUserInfoUseCase,
    private val getUserServersUseCase: GetUserServersUseCase,
    private val saveUserPhotoUseCase: SaveUserPhotoUseCase,
    private val changeUserNameUseCase: ChangeUserNameUseCase,
    private val changeUserStatusUseCase: ChangeUserStatusUseCase,
    private val changeUserDescUseCase: ChangeUserDescUseCase,

    // Friends
    private val getUserFriendsUseCase: GetUserFriendsUseCase,
    private val createFriendRequestUseCase: CreateFriendRequestUseCase,
    private val deleteFriendshipUseCase: DeleteFriendshipUseCase,
    private val respondToFriendshipRequestUseCase: RespondToFriendshipRequestUseCase,
    private val searchFriendsUseCase: SearchFriendsUseCase,
    private val getUserInfoExtendedUseCase: GetUserInfoExtendedUseCase,
    private val getChosenUserCommonServersUseCase: GetChosenUserCommonServersUseCase,

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

    private val _loadChosenUserEvent = MutableStateFlow<LoadChosenUserEvent?>(null)
    val loadChosenUserEvent: StateFlow<LoadChosenUserEvent?> = _loadChosenUserEvent

    private val _loadChosenUserCommonServersEvent =
        MutableStateFlow<LoadChosenUserCommonServersEvent?>(null)
    val loadChosenUserCommonServersEvent: StateFlow<LoadChosenUserCommonServersEvent?> =
        _loadChosenUserCommonServersEvent

    private val _savePhotoEvent = MutableStateFlow<SavePhotoEvent?>(null)
    val savePhotoEvent: StateFlow<SavePhotoEvent?> = _savePhotoEvent

    private val _saveUserNameEvent = MutableStateFlow<SaveUserNameEvent?>(null)
    val saveUserNameEvent: StateFlow<SaveUserNameEvent?> = _saveUserNameEvent

    private val _saveUserStatusEvent = MutableStateFlow<SaveUserStatusEvent?>(null)
    val saveUserStatusEvent: StateFlow<SaveUserStatusEvent?> = _saveUserStatusEvent

    private val _saveUserDescEvent = MutableStateFlow<SaveUserDescEvent?>(null)
    val saveUserDescEvent: StateFlow<SaveUserDescEvent?> = _saveUserDescEvent

    private val _createFriendRequestEvent = MutableStateFlow<CreateFriendRequestEvent?>(null)
    val createFriendRequestEvent: StateFlow<CreateFriendRequestEvent?> = _createFriendRequestEvent

    private val _deleteFriendshipEvent = MutableStateFlow<DeleteFriendshipEvent?>(null)
    val deleteFriendshipEvent: StateFlow<DeleteFriendshipEvent?> = _deleteFriendshipEvent

    private val _respondToFriendshipRequestEvent =
        MutableStateFlow<RespondToFriendRequestEvent?>(null)
    val respondToFriendshipRequestEvent: StateFlow<RespondToFriendRequestEvent?> =
        _respondToFriendshipRequestEvent

    val originalUsername = mutableStateOf("")
    val originalStatusPresentation = mutableStateOf(StatusPresentation.INVISIBLE)
    val originalDescription = mutableStateOf("")
    val originalFriends = mutableStateListOf<FriendUiModel>()

    val isUsernameMatched = mutableStateOf(false)
    val showStatusSheet = mutableStateOf(false)
    val showExitSheet = mutableStateOf(false)
    val showFriendCard = mutableStateOf(false)
    val showCommonServers = mutableStateOf(false)

    val searchValueFriends = mutableStateOf("")
    val addFriendFieldValue = mutableStateOf("")

    val errorAddFriends = mutableStateOf(false)
    val infoTextAddFriend = mutableStateOf("")

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
                    originalStatusPresentation.value = updatedData.status
                    originalDescription.value = updatedData.description

                    originalFriends.addAll(updatedData.friends)

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

    fun loadChosenUser(userId: String) {
        viewModelScope.launch {
            val result = getUserInfoExtendedUseCase(userId)

            _loadChosenUserEvent.value = result.fold(
                onSuccess = { user ->
                    val currentState = _uiState.value
                    if (currentState is ProfileUiState.Success) {
                        val updatedData = currentState.data.copy(
                            chosenUser = user.toUi()
                        )
                        _uiState.value = ProfileUiState.Success(updatedData)
                    }
                    LoadChosenUserEvent.SuccessLoad
                },
                onFailure = {
                    LoadChosenUserEvent.Error(
                        ("Ошибка при загрузке информации о пользователе. " + it.message)
                    )
                }
            )
        }
    }

    fun loadChosenUserCommonServers(userId: String) {
        viewModelScope.launch {
            val result = getChosenUserCommonServersUseCase(userId)

            _loadChosenUserCommonServersEvent.value = result.fold(
                onSuccess = { servers ->
                    val currentState = _uiState.value
                    if (currentState is ProfileUiState.Success) {
                        val updatedData = currentState.data.copy(
                            chosenUserCommonServers = servers.map { it.toUi() }
                        )
                        _uiState.value = ProfileUiState.Success(updatedData)
                    }
                    LoadChosenUserCommonServersEvent.SuccessLoad
                },
                onFailure = {
                    LoadChosenUserCommonServersEvent.Error(
                        ("Ошибка при загрузке информации об общих серверах с пользователем. " + it.message)
                    )
                }
            )
        }
    }

    fun getIncomingRequests(friends: List<FriendUiModel>): List<FriendUiModel> {
        return friends.filter { friendUiModel -> friendUiModel.type == TypeUiModel.INCOMING_REQUEST }
    }

    fun getOutgoingRequests(friends: List<FriendUiModel>): List<FriendUiModel> {
        return friends.filter { friendUiModel -> friendUiModel.type == TypeUiModel.OUTGOING_REQUEST }
    }

    fun getFriends(friends: List<FriendUiModel>): List<FriendUiModel> {
        return friends.filter { friendUiModel -> friendUiModel.type == TypeUiModel.FRIEND }
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
            saveUserPhoto(uri)
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
                ProfileUiState.Success(updatedData)
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
                        originalUsername.value = data.username
                        SaveUserNameEvent.SuccessSave
                    },
                    onFailure = {
                        val updatedData = currentState.data.copy(
                            username = originalUsername.value
                        )
                        _uiState.value = ProfileUiState.Success(updatedData)
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
                        originalDescription.value = data.description
                        SaveUserDescEvent.SuccessSave
                    },
                    onFailure = {
                        val updatedData = currentState.data.copy(
                            description = originalDescription.value
                        )
                        _uiState.value = ProfileUiState.Success(updatedData)
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
                        onSuccess = { url ->
                            val updatedData = currentState.data.copy(
                                profilePictureUrl = url
                            )
                            _uiState.value = ProfileUiState.Success(updatedData)
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
                        originalStatusPresentation.value = status
                        SaveUserStatusEvent.SuccessSave
                    },
                    onFailure = {
                        SaveUserStatusEvent.Error("Ошибка при сохранении статуса. " + it.message)
                    }
                )
            }
        }
    }

    fun onSearchValueChange(value: String) {
        searchValueFriends.value = value
        val currentState = _uiState.value
        if (currentState is ProfileUiState.Success) {
            viewModelScope.launch {
                val result = searchFriendsUseCase(originalFriends.map { it }, value)
                val updatedData = currentState.data.copy(
                    friends = result.map { it }
                )
                _uiState.value = ProfileUiState.Success(updatedData)
            }
        }
    }

    fun changeAddFriendValue(value: String) {
        addFriendFieldValue.value = value
    }

    fun respondFriend(user: FriendUiModel, status: String) {
        viewModelScope.launch {
            val result =
                respondToFriendshipRequestUseCase(user.id/*todo не забыть про айди дружбы*/, status)

            _respondToFriendshipRequestEvent.value = result.fold(
                onSuccess = {
                    RespondToFriendRequestEvent.SuccessRespond
                },
                onFailure = {
                    RespondToFriendRequestEvent.Error("Ошибка при изменении данных. " + it.message)
                }
            )
        }
    }

    fun deleteFriendship(userId: String) {
        //todo
    }

    fun createFriendshipRequest(nickname: String) {
        viewModelScope.launch {
            val result = createFriendRequestUseCase(nickname)

            _createFriendRequestEvent.value = result.fold(
                onSuccess = {
                    CreateFriendRequestEvent.SuccessRequest(nickname)
                },
                onFailure = {
                    CreateFriendRequestEvent.Error("Ошибка при создании заявки" + it.message)
                }
            )
        }
    }

    fun onCallClick(userId: String) {
        //TODO
    }

    fun onMessageClick(userId: String) {
        //TODO
    }

    fun onVideoCallClick(userId: String) {
        //TODO
    }

    fun onDismiss() {
        onSelectedStatusChanged(originalStatusPresentation.value)
    }

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        toastManager.showToast(
            message = message,
            duration = duration
        )
    }

    fun exit() {
        showExitSheet.value = false
        dataManager.clearJwt()
        dataManager.clearVerificationStatus()
    }

    fun resetSaveUserNameEvent() {
        _saveUserNameEvent.value = null
    }

    fun resetSaveUserStatusEvent() {
        _saveUserStatusEvent.value = null
    }

    fun resetSaveUserDescEvent() {
        _saveUserDescEvent.value = null
    }

    fun resetSavePhotoEvent() {
        _savePhotoEvent.value = null
    }

    fun resetLoadChosenUserEvent() {
        _loadChosenUserEvent.value = null
    }

    fun resetLoadChosenUserCommonServersEvent() {
        _loadChosenUserCommonServersEvent.value = null
    }

    fun resetRespondToFriendRequestEvent() {
        _respondToFriendshipRequestEvent.value = null
    }

    fun resetCreateFriendRequestEvent() {
        _createFriendRequestEvent.value = null
    }
}