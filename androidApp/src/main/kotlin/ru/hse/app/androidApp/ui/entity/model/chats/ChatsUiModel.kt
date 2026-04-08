package ru.hse.app.androidApp.ui.entity.model.chats

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.ui.entity.model.ChatShortUiModel
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel

sealed interface ChatsUiState {
    data object Loading : ChatsUiState
    data class Success(val data: ChatsUiModel) : ChatsUiState
    data class Error(val message: String) : ChatsUiState
}

@Immutable
data class ChatsUiModel(
    val chats: List<ChatShortUiModel>,
    val friends: List<FriendUiModel>
)

//todo убрать
val mockChats = listOf(
    ChatShortUiModel(
        id = "1",
        title = "Марина Ландышева",
        lastMessage = "Вы: пришли мне, пожалуйста, последнюю картинку",
        timeOfLastMessage = "15:16",
        chatPhotoUrl = "",
        unreadCount = 0
    ),
    ChatShortUiModel(
        id = "2",
        title = "Александр Иванов",
        lastMessage = "Завтра встреча в 10:00",
        timeOfLastMessage = "12:30",
        chatPhotoUrl = "",
        unreadCount = 3
    ),
    ChatShortUiModel(
        id = "3",
        title = "Рабочая группа",
        lastMessage = "Марина: Я отправила финальный отчет",
        timeOfLastMessage = "10:15",
        chatPhotoUrl = "",
        unreadCount = 0
    ),
    ChatShortUiModel(
        id = "4",
        title = "Сергей Петров",
        lastMessage = "Спасибо за помощь!",
        timeOfLastMessage = "09:45",
        chatPhotoUrl = "",
        unreadCount = 1
    ),
    ChatShortUiModel(
        id = "5",
        title = "Поддержка",
        lastMessage = "Ваш вопрос решен",
        timeOfLastMessage = "Вчера",
        chatPhotoUrl = "",
        unreadCount = 0
    ),
    ChatShortUiModel(
        id = "6",
        title = "Дизайн-команда",
        lastMessage = "Новые макеты готовы",
        timeOfLastMessage = "Вчера",
        chatPhotoUrl = "",
        unreadCount = 5
    )
)