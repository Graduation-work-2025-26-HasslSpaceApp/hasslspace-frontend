package ru.hse.app.androidApp.ui.components.chats.mychats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.bar.SearchBar
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.card.ChatCard
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.ChatShortUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun MyChatsScreenContent(
    imageLoader: ImageLoader,
    chats: List<ChatShortUiModel>,
    onAddClick: () -> Unit,
    onChatClick: (ChatShortUiModel) -> Unit,
    searchText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            VariableBold(
                text = "Мои чаты",
                fontSize = 28.sp,
                modifier = Modifier.weight(1f)
            )
            AddTextButton(
                onClick = onAddClick
            )
        }
        Spacer(Modifier.height(15.dp))
        SearchBar(
            text = searchText,
            onValueChange = onValueChange
        )
        Spacer(Modifier.height(15.dp))

        if (chats.isEmpty()) {
            NoItemsBox("У вас пока нет чатов")
        }

        UniversalVerticalGrid(
            items = chats,
            columns = 1,
            contentPadding = PaddingValues(0.dp),
        ) { chat ->
            ChatCard(
                imageLoader = imageLoader,
                title = chat.title,
                lastMessage = chat.lastMessage,
                timeOfLastMessage = chat.timeOfLastMessage,
                chatPictureUrl = chat.chatPhotoUrl,
                isDarkTheme = isDarkTheme,
                onChatClick = { onChatClick(chat) },
                unreadCount = chat.unreadCount
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun MyChatsScreenContentPreviewLight() {
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

    AppTheme(isDark = false) {
        MyChatsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            chats = mockChats,
            onAddClick = {},
            onChatClick = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = false, backgroundColor = 0xFF121212)
@Composable
fun MyChatsScreenContentPreviewDark() {
    val mockChats = listOf(
        ChatShortUiModel(
            id = "1",
            title = "Марина Ландышева",
            lastMessage = "Вы: пришли мне, пожалуйста, последнюю картинку",
            timeOfLastMessage = "15:16",
            chatPhotoUrl = "",
            unreadCount = 5
        ),
        ChatShortUiModel(
            id = "2",
            title = "Александр Иванов",
            lastMessage = "Завтра встреча в 10:00",
            timeOfLastMessage = "12:30",
            chatPhotoUrl = "",
            unreadCount = 55
        ),
        ChatShortUiModel(
            id = "3",
            title = "Поддержка",
            lastMessage = "Ваш вопрос решен",
            timeOfLastMessage = "Вчера",
            chatPhotoUrl = "",
            unreadCount = 555
        )
    )

    AppTheme(isDark = true) {
        MyChatsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            chats = mockChats,
            onAddClick = {},
            onChatClick = {},
            searchText = "поиск",
            onValueChange = {},
            isDarkTheme = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun MyChatsScreenContentPreviewEmptyLight() {
    AppTheme(isDark = false) {
        MyChatsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            chats = emptyList(),
            onAddClick = {},
            onChatClick = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun MyChatsScreenContentPreviewWithSearchLight() {
    val mockChats = listOf(
        ChatShortUiModel(
            id = "1",
            title = "Марина",
            lastMessage = "Вы: пришли мне, пожалуйста, последнюю картинку",
            timeOfLastMessage = "15:16",
            chatPhotoUrl = "",
            unreadCount = 0
        ),
        ChatShortUiModel(
            id = "2",
            title = "Мария",
            lastMessage = "Когда сможем встретиться?",
            timeOfLastMessage = "11:20",
            chatPhotoUrl = "",
            unreadCount = 2
        )
    )

    AppTheme(isDark = false) {
        MyChatsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            chats = mockChats,
            onAddClick = {},
            onChatClick = {},
            searchText = "мар",
            onValueChange = {},
            isDarkTheme = false
        )
    }
}