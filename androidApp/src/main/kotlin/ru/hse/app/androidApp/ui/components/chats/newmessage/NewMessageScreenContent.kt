package ru.hse.app.androidApp.ui.components.chats.newmessage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.bar.SearchBar
import ru.hse.app.androidApp.ui.components.common.button.AddButton
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.UserCardArrow
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun NewMessageScreenContent(
    imageLoader: ImageLoader,
    friends: List<FriendUiModel>,
    onBackClick: () -> Unit,
    onFriendClick: (FriendUiModel) -> Unit,
    onAddFriendClick: () -> Unit,
    onNewGroupClick: () -> Unit,
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
            BackButton(onClick = onBackClick)
            Spacer(Modifier.width(10.dp))
            VariableBold(
                text = "Новое сообщение",
                fontSize = 28.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(15.dp))
        SearchBar(
            placeholder = "Кому? Найти друзей",
            text = searchText,
            onValueChange = onValueChange
        )
        Spacer(Modifier.height(15.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier,
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AddButton(
                    onClick = onNewGroupClick,
                    text = "Новая группа",
                    iconResource = if (isDarkTheme) R.drawable.new_group_dark else R.drawable.new_group_light
                )
            }

            item {
                AddButton(
                    modifier = Modifier.padding(bottom = 15.dp),
                    onClick = onAddFriendClick,
                    text = "Добавить друга",
                    iconResource = if (isDarkTheme) R.drawable.new_friend_dark else R.drawable.new_friend_light
                )
            }

            items(friends, key = { it.id }) { friend ->
                UserCardArrow(
                    imageLoader = imageLoader,
                    username = friend.name,
                    nickname = friend.nickname,
                    profilePictureUrl = friend.avatarUrl,
                    status = friend.status,
                    isDarkTheme = isDarkTheme,
                    onCardClick = { onFriendClick(friend) },
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewMessageScreenContentPreviewLight() {
    val mockFriends = listOf(
        FriendUiModel(
            id = "1",
            name = "Марина Ландышева",
            nickname = "@marina_flower",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        ),
        FriendUiModel(
            id = "2",
            name = "Александр Иванов",
            nickname = "@alex_ivanov",
            avatarUrl = "",
            status = StatusPresentation.INVISIBLE
        ),
        FriendUiModel(
            id = "3",
            name = "Сергей Петров",
            nickname = "@sergey_p",
            avatarUrl = "",
            status = StatusPresentation.DO_NOT_DISTURB
        ),
        FriendUiModel(
            id = "4",
            name = "Екатерина Смирнова",
            nickname = "@katya_smirnova",
            avatarUrl = "",
            status = StatusPresentation.NOT_ACTIVE
        ),
        FriendUiModel(
            id = "5",
            name = "Дмитрий Козлов",
            nickname = "@dmitry_k",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        ),
        FriendUiModel(
            id = "6",
            name = "Анна Морозова",
            nickname = "@anna_moroz",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        )
    )

    AppTheme(isDark = false) {
        NewMessageScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = mockFriends,
            onBackClick = {},
            onFriendClick = {},
            onAddFriendClick = {},
            onNewGroupClick = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = false, backgroundColor = 0xFF121212)
@Composable
fun NewMessageScreenContentPreviewDark() {
    val mockFriends = listOf(
        FriendUiModel(
            id = "1",
            name = "Марина Ландышева",
            nickname = "@marina_flower",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        ),
        FriendUiModel(
            id = "2",
            name = "Александр Иванов",
            nickname = "@alex_ivanov",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        ),
        FriendUiModel(
            id = "3",
            name = "Сергей Петров",
            nickname = "@sergey_p",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        ),
        FriendUiModel(
            id = "4",
            name = "Екатерина Смирнова",
            nickname = "@katya_smirnova",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        )
    )

    AppTheme(isDark = true) {
        NewMessageScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = mockFriends,
            onBackClick = {},
            onFriendClick = {},
            onAddFriendClick = {},
            onNewGroupClick = {},
            searchText = "мар",
            onValueChange = {},
            isDarkTheme = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewMessageScreenContentPreviewEmptyLight() {
    AppTheme(isDark = false) {
        NewMessageScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = emptyList(),
            onBackClick = {},
            onFriendClick = {},
            onAddFriendClick = {},
            onNewGroupClick = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, showSystemUi = false, backgroundColor = 0xFF121212)
@Composable
fun NewMessageScreenContentPreviewEmptyDark() {
    AppTheme(isDark = true) {
        NewMessageScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = emptyList(),
            onBackClick = {},
            onFriendClick = {},
            onAddFriendClick = {},
            onNewGroupClick = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = true
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewMessageScreenContentPreviewWithSearchLight() {
    val mockFriends = listOf(
        FriendUiModel(
            id = "1",
            name = "Марина Ландышева",
            nickname = "@marina_flower",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        ),
        FriendUiModel(
            id = "2",
            name = "Мария Иванова",
            nickname = "@masha_ivanova",
            avatarUrl = "",
            status = StatusPresentation.ACTIVE
        )
    )

    AppTheme(isDark = false) {
        NewMessageScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = mockFriends,
            onBackClick = {},
            onFriendClick = {},
            onAddFriendClick = {},
            onNewGroupClick = {},
            searchText = "мар",
            onValueChange = {},
            isDarkTheme = false
        )
    }
}