package ru.hse.app.hasslspace.screen.servers

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.hse.app.hasslspace.data.local.DataManager
import ru.hse.app.hasslspace.domain.service.common.CropProfilePhotoService
import ru.hse.app.hasslspace.domain.usecase.servers.CreateServerUseCase
import ru.hse.app.hasslspace.domain.usecase.servers.GetServersUseCase
import ru.hse.app.hasslspace.domain.usecase.servers.JoinServerUseCase
import ru.hse.app.hasslspace.domain.usecase.servers.SearchServersUseCase
import ru.hse.app.hasslspace.ui.entity.model.ServerShortUiModel
import ru.hse.app.hasslspace.ui.entity.model.servers.ServersUiModel
import ru.hse.app.hasslspace.ui.entity.model.servers.ServersUiState
import ru.hse.app.hasslspace.ui.entity.model.servers.events.CreateServerEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.GetUserServersEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.JoinServerEvent
import ru.hse.app.hasslspace.ui.entity.model.toUi
import ru.hse.app.hasslspace.ui.errorhandling.ErrorHandler
import ru.hse.coursework.godaily.ui.notification.ToastManager
import javax.inject.Inject

@HiltViewModel
class ServersViewModel @Inject constructor(
    private val createServerUseCase: CreateServerUseCase,
    private val getServersUseCase: GetServersUseCase,

    private val joinServerUseCase: JoinServerUseCase,
    private val searchServersUseCase: SearchServersUseCase,

    private val errorHandler: ErrorHandler,

    private val dataManager: DataManager,

    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val imageLoader: ImageLoader

) : ViewModel() {

    val isDarkTheme = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ServersUiState>(ServersUiState.Loading)
    val uiState: StateFlow<ServersUiState> = _uiState

    private val _createServerEvent = MutableStateFlow<CreateServerEvent?>(null)
    val createServerEvent: StateFlow<CreateServerEvent?> = _createServerEvent
    private val _getUserServersEvent = MutableStateFlow<GetUserServersEvent?>(null)
    val getUserServersEvent: StateFlow<GetUserServersEvent?> = _getUserServersEvent

    private val _joinServerEvent = MutableStateFlow<JoinServerEvent?>(null)
    val joinServerEvent: StateFlow<JoinServerEvent?> = _joinServerEvent

    // Servers Screen
    val originalServers = mutableStateListOf<ServerShortUiModel>()
    val searchServersText = mutableStateOf("")

    // Create Server Screen
    val selectedImageUri: MutableState<Uri?> = mutableStateOf(null)
    val selectedServerName = mutableStateOf("")

    // Join server Screen
    val linkTextServer = mutableStateOf("")

    init {
        loadUserServers()
    }

    fun loadUserServers() {
        viewModelScope.launch {
            val result = getServersUseCase()

            _getUserServersEvent.value = result.fold(
                onSuccess = { servers ->
                    val serversUi = servers.map { it.toUi() }
                    _uiState.value = ServersUiState.Success(
                        data = ServersUiModel(
                            userServers = serversUi,
                        )
                    )
                    originalServers.clear()
                    originalServers.addAll(serversUi)

                    GetUserServersEvent.SuccessLoad
                },
                onFailure = {
                    _uiState.value = ServersUiState.Error("") // todo
                    GetUserServersEvent.Error(
                        ("Ошибка при загрузке серверов. " + it.message)
                    )
                }
            )
        }
    }

    fun onSearchServersValueChange(value: String) {
        searchServersText.value = value
        val currentState = _uiState.value
        if (currentState is ServersUiState.Success) {
            viewModelScope.launch {
                val result = searchServersUseCase(originalServers.map { it }, value)
                val updatedData = currentState.data.copy(
                    userServers = result.map { it }
                )
                _uiState.value = ServersUiState.Success(updatedData)
            }
        }
    }

    fun onLinkServerValueChange(value: String) {
        linkTextServer.value = value
    }

    fun onSelectedServerNameChanged(value: String) {
        selectedServerName.value = value
    }

    fun onPhotoUriChanged(uri: Uri?) {
        selectedImageUri.value = uri
    }

    fun createServer(
        serverName: String,
        serverPhoto: Uri? = null,
    ) {
        if (serverName.isEmpty()) {
            handleError("Название сервера не может быть пустым")
            return
        }
        viewModelScope.launch {
            val result = createServerUseCase(serverName, serverPhoto)

            _createServerEvent.value = result.fold(
                onSuccess = { server ->
                    CreateServerEvent.SuccessCreate
                },
                onFailure = { error ->
                    CreateServerEvent.Error("Ошибка при создании сервера. ${error.message}")
                }
            )
        }
    }

    fun joinServer(code: String) {
        val linkPattern = Regex("^https://hasslspace\\.ru/[a-zA-Z0-9]+$")

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

    fun handleError(message: String) {
        errorHandler.handleError(
            message = message
        )
    }

    fun resetCreateServerEvent() {
        _createServerEvent.value = null
    }

    fun resetGetUserServersEvent() {
        _getUserServersEvent.value = null
    }

    fun resetJoinServerEvent() {
        _joinServerEvent.value = null
    }
}