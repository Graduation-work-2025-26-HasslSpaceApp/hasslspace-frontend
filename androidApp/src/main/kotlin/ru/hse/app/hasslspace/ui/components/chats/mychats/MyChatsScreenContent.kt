package ru.hse.app.hasslspace.ui.components.chats.mychats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import ru.hse.app.hasslspace.ui.components.chats.chat.formatMessageTime
import ru.hse.app.hasslspace.ui.components.common.bar.SearchBar
import ru.hse.app.hasslspace.ui.components.common.box.NoItemsBox
import ru.hse.app.hasslspace.ui.components.common.button.AddTextButton
import ru.hse.app.hasslspace.ui.components.common.card.ChatCard
import ru.hse.app.hasslspace.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.hasslspace.ui.components.common.text.VariableBold
import ru.hse.app.hasslspace.ui.entity.model.ChatShortUiModel
import ru.hse.app.hasslspace.ui.theme.AppTheme

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
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
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
            verticalSpacing = 20.dp,
        ) { chat ->
            val sorted = chat.messages.sortedByDescending { it.timestamp }
            ChatCard(
                imageLoader = imageLoader,
                title = chat.name,
                lastMessage = sorted.firstOrNull()?.content ?: "",
                timeOfLastMessage = if (sorted.isNotEmpty() && sorted.firstOrNull()?.timestamp != null) {
                    formatMessageTime(sorted.firstOrNull()!!.timestamp)
                } else {
                    ""
                },
                chatPictureUrl = chat.chatPhotoUrl,
                isDarkTheme = isDarkTheme,
                onChatClick = { onChatClick(chat) },
                unreadCount = chat.unreadCount,
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
            name = "Марина Ландышева",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "2",
            name = "Александр Иванов",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 3,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "3",
            name = "Рабочая группа",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "4",
            name = "Сергей Петров",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 1,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "5",
            name = "Поддержка",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "6",
            name = "Дизайн-команда",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 5,
            channelMembers = listOf(),
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
            name = "Марина Ландышева",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "2",
            name = "Александр Иванов",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 3,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "3",
            name = "Рабочая группа",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "4",
            name = "Сергей Петров",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 1,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "5",
            name = "Поддержка",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "6",
            name = "Дизайн-команда",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 5,
            channelMembers = listOf(),
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
            name = "Марина Ландышева",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "2",
            name = "Александр Иванов",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 3,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "3",
            name = "Рабочая группа",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "4",
            name = "Сергей Петров",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 1,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "5",
            name = "Поддержка",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 0,
            channelMembers = listOf(),
        ),
        ChatShortUiModel(
            id = "6",
            name = "Дизайн-команда",
            messages = listOf(),
            chatPhotoUrl = "",
            unreadCount = 5,
            channelMembers = listOf(),
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