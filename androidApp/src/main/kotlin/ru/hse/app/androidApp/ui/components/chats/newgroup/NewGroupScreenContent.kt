package ru.hse.app.androidApp.ui.components.chats.newgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.bar.SearchBar
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.UserCardCheckbox
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun NewGroupScreenContent(
    imageLoader: ImageLoader,
    friends: List<FriendCheckboxUiModel>,
    onBackClick: () -> Unit,
    onCreateClick: () -> Unit,
    onToggle: (FriendCheckboxUiModel) -> Unit,
    searchText: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    userCount: Int,
    limitCount: Int = 10,
    isDarkTheme: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BackButton(onClick = onBackClick)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                VariableBold(
                    text = "Новая группа",
                    fontSize = 20.sp,
                )
                VariableLight(
                    text = "Участников: $userCount из $limitCount",
                    fontSize = 14.sp,
                    fontColor = MaterialTheme.colorScheme.outline,
                )
            }

            AddTextButton(
                onClick = onCreateClick
            )
        }
        Spacer(Modifier.height(15.dp))
        SearchBar(
            placeholder = "Кого добавим?",
            text = searchText,
            onValueChange = onValueChange
        )
        Spacer(Modifier.height(15.dp))

        UniversalVerticalGrid(
            items = friends,
            columns = 1,
            contentPadding = PaddingValues(0.dp),
        ) { friend ->
            UserCardCheckbox(
                imageLoader = imageLoader,
                username = friend.name,
                nickname = friend.nickname,
                status = friend.status,
                profilePictureUrl = friend.avatarUrl,
                isDarkTheme = isDarkTheme,
                onClick = { onToggle(friend) },
                isChosen = friend.isChosen
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewGroupScreenContentPreviewLight() {
    val mockFriends = listOf(
        FriendCheckboxUiModel(
            id = "1",
            name = "Марина Ландышева",
            nickname = "marina_flower",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "2",
            name = "Александр Иванов",
            nickname = "alex_ivanov",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = false
        ),
        FriendCheckboxUiModel(
            id = "3",
            name = "Сергей Петров",
            nickname = "sergey_p",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "4",
            name = "Екатерина Смирнова",
            nickname = "katya_smirnova",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = false
        ),
        FriendCheckboxUiModel(
            id = "5",
            name = "Дмитрий Козлов",
            nickname = "dmitry_k",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = false
        ),
        FriendCheckboxUiModel(
            id = "6",
            name = "Анна Морозова",
            nickname = "anna_moroz",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "7",
            name = "Иван Сидоров",
            nickname = "ivan_sidorov",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = false
        )
    )

    AppTheme(isDark = false) {
        NewGroupScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = mockFriends,
            onBackClick = {},
            onCreateClick = {},
            onToggle = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = false,
            userCount = 3
        )
    }
}

@Preview(showBackground = true, showSystemUi = false, backgroundColor = 0xFF121212)
@Composable
fun NewGroupScreenContentPreviewDark() {
    val mockFriends = listOf(
        FriendCheckboxUiModel(
            id = "1",
            name = "Марина Ландышева",
            nickname = "marina_flower",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "2",
            name = "Александр Иванов",
            nickname = "alex_ivanov",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = false
        ),
        FriendCheckboxUiModel(
            id = "3",
            name = "Сергей Петров",
            nickname = "sergey_p",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "4",
            name = "Екатерина Смирнова",
            nickname = "katya_smirnova",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = false
        )
    )

    AppTheme(isDark = true) {
        NewGroupScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = mockFriends,
            onBackClick = {},
            onCreateClick = {},
            onToggle = {},
            searchText = "мар",
            onValueChange = {},
            isDarkTheme = true,
            userCount = 3
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewGroupScreenContentPreviewEmptyLight() {
    AppTheme(isDark = false) {
        NewGroupScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = emptyList(),
            onBackClick = {},
            onCreateClick = {},
            onToggle = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = false,
            userCount = 3
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewGroupScreenContentPreviewWithSearchLight() {
    val mockFriends = listOf(
        FriendCheckboxUiModel(
            id = "1",
            name = "Марина Ландышева",
            nickname = "marina_flower",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "2",
            name = "Мария Иванова",
            nickname = "masha_ivanova",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = false
        )
    )

    AppTheme(isDark = false) {
        NewGroupScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = mockFriends,
            onBackClick = {},
            onCreateClick = {},
            onToggle = {},
            searchText = "Mah",
            onValueChange = {},
            isDarkTheme = false,
            userCount = 3
        )
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun NewGroupScreenContentPreviewMultiSelectionLight() {
    val mockFriends = listOf(
        FriendCheckboxUiModel(
            id = "1",
            name = "Марина Ландышева",
            nickname = "marina_flower",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "2",
            name = "Александр Иванов",
            nickname = "alex_ivanov",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "3",
            name = "Сергей Петров",
            nickname = "sergey_p",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = true
        ),
        FriendCheckboxUiModel(
            id = "4",
            name = "Екатерина Смирнова",
            nickname = "katya_smirnova",
            status = StatusPresentation.ACTIVE,
            avatarUrl = "",
            isChosen = false
        )
    )

    AppTheme(isDark = false) {
        NewGroupScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = mockFriends,
            onBackClick = {},
            onCreateClick = {},
            onToggle = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = false,
            userCount = 3
        )
    }
}