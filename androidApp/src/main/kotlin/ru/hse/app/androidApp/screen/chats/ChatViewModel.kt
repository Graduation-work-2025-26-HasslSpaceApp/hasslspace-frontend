package ru.hse.app.androidApp.screen.chats

import android.os.Message
import androidx.lifecycle.ViewModel
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.hse.app.androidApp.data.local.DataManager
import ru.hse.app.androidApp.domain.service.common.CropProfilePhotoService
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.coursework.godaily.ui.notification.ToastManager
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val dataManager: DataManager,
    private val toastManager: ToastManager,
    val cropProfilePhotoService: CropProfilePhotoService,
    val imageLoader: ImageLoader
) : ViewModel() {
    val isDark = dataManager.isDark.value

    private val _uiState =
        MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState

    fun loadChatInitInfo(chatId: String) {
        // todo загружаем изначальный стейт для чата и сообщений
        _uiState.value = ChatUiState.Success(
            data = ChatUiModel(
                id = chatId,
                channelName = "composers",
                channelMembers = listOf(),
                currentUser = ServerMemberUiModel(
                    id = "q",
                    name = "Юлия Кухтина",
                    nickname = "yuulkht",
                    status = StatusPresentation.ACTIVE,
                    avatarUrl = "",
                    mainRole = null,
                    allRoles = listOf(),
                    isOwner = true
                ),
                messages = listOf(),
            )
        )
    }

    fun addCurrentUserMessage(content: String) {
        val time = LocalDateTime.now()
        // todo отправляем инфо о сообщении в центрифугу и если успешно
        val currentState = _uiState.value
        if (currentState is ChatUiState.Success) {
            val message = MessageUiModel(
                author = currentState.data.currentUser,
                content = content,
                timestamp = time
            )

            updateChatWithMessage(message)
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
}