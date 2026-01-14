package ru.hse.app.androidApp.ui.components.profile.friends

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import ru.hse.app.androidApp.ui.components.common.bar.SearchBar
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.UserCardAcceptDismiss
import ru.hse.app.androidApp.ui.components.common.card.UserCardCallMessage
import ru.hse.app.androidApp.ui.components.common.state.StatusPresentation
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun FriendsContent(
    imageLoader: ImageLoader,
    friends: List<FriendUiModel>,
    applications: List<FriendUiModel>,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onFriendClick: (FriendUiModel) -> Unit,
    onCallClick: (FriendUiModel) -> Unit,
    onMessageClick: (FriendUiModel) -> Unit,
    onApplicationClick: (FriendUiModel) -> Unit,
    onAcceptClick: (FriendUiModel) -> Unit,
    onDismissClick: (FriendUiModel) -> Unit,
    searchText: MutableState<String>,
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
                text = "Друзья",
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

        if (applications.isEmpty() && friends.isEmpty()) {
            NoItemsBox("Вы пока не добавили друзей в HasslSpace")
        }

        if (applications.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = modifier,
                contentPadding = PaddingValues(0.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    VariableLight(
                        text = "Заявки в друзья",
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(5.dp))
                }

                items(applications, key = { it.id }) { user ->
                    UserCardAcceptDismiss(
                        imageLoader = imageLoader,
                        username = user.name,
                        nickname = user.nickname,
                        status = user.status,
                        profilePictureUrl = user.avatarUrl,
                        isDarkTheme = isDarkTheme,
                        onCardClick = { onApplicationClick(user) },
                        onAcceptClick = { onAcceptClick(user) },
                        onDismissClick = { onDismissClick(user) }
                    )
                }

                item {
                    VariableLight(
                        text = "Мои друзья",
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(5.dp))
                }

                items(friends, key = { it.id }) { friend ->
                    UserCardCallMessage(
                        imageLoader = imageLoader,
                        username = friend.name,
                        nickname = friend.nickname,
                        status = friend.status,
                        profilePictureUrl = friend.avatarUrl,
                        isDarkTheme = isDarkTheme,
                        onCardClick = { onFriendClick(friend) },
                        onCallClick = { onCallClick(friend) },
                        onMessageClick = { onMessageClick(friend) }
                    )
                }
            }
        } else {
            FriendsList(
                contentPadding = PaddingValues(0.dp),
                imageLoader = imageLoader,
                isDarkTheme = isDarkTheme,
                friends = friends,
                onFriendClick = onFriendClick,
                onCallClick = onCallClick,
                onMessageClick = onMessageClick
            )

        }
    }
}

private fun previewFriend(id: String, name: String) = FriendUiModel(
    id = id,
    name = name,
    nickname = name.lowercase().replace(" ", "_"),
    avatarUrl = "",
    status = StatusPresentation.ACTIVE
)

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun FriendsContentPreviewWithRequestsLight() {
    val friends = listOf(
        previewFriend("1", "Александр Иванов"),
        previewFriend("2", "Мария Петрова"),
        previewFriend("3", "Дмитрий Сидоров"),
        previewFriend("4", "Александр Иванов"),
        previewFriend("5", "Мария Петрова"),
        previewFriend("6", "Дмитрий Сидоров"),
        previewFriend("7", "Александр Иванов"),
        previewFriend("8", "Мария Петрова"),
        previewFriend("9", "Дмитрий Сидоров"),
        previewFriend("10", "Александр Иванов"),
        previewFriend("11", "Мария Петрова"),
        previewFriend("12", "Дмитрий Сидоров"),
    )

    val applications = listOf(
        previewFriend("10", "Анна Смирнова"),
        previewFriend("11", "Игорь Кузнецов")
    )

    AppTheme(isDark = false) {
        FriendsContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = friends,
            applications = applications,
            onBackClick = {},
            onAddClick = {},
            onFriendClick = {},
            onCallClick = {},
            onMessageClick = {},
            onApplicationClick = {},
            onAcceptClick = {},
            onDismissClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - Only Friends (Light)")
@Composable
fun FriendsContentPreviewOnlyFriendsLight() {
    val friends = listOf(
        previewFriend("1", "Александр Иванов"),
        previewFriend("2", "Мария Петрова"),
        previewFriend("3", "Дмитрий Сидоров"),
        previewFriend("4", "Екатерина Волкова")
    )

    AppTheme(isDark = false) {
        FriendsContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = friends,
            applications = emptyList(),
            onBackClick = {},
            onAddClick = {},
            onFriendClick = {},
            onCallClick = {},
            onMessageClick = {},
            onApplicationClick = {},
            onAcceptClick = {},
            onDismissClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - With Requests (Dark)")
@Composable
fun FriendsContentPreviewWithRequestsDark() {
    val friends = listOf(
        previewFriend("1", "Александр Иванов"),
        previewFriend("2", "Мария Петрова"),
        previewFriend("3", "Дмитрий Сидоров"),
    )

    val applications = listOf(
        previewFriend("10", "Анна Смирнова"),
        previewFriend("11", "Игорь Кузнецов")
    )

    AppTheme(isDark = true) {
        FriendsContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = friends,
            applications = applications,
            onBackClick = {},
            onAddClick = {},
            onFriendClick = {},
            onCallClick = {},
            onMessageClick = {},
            onApplicationClick = {},
            onAcceptClick = {},
            onDismissClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = true
        )
    }
}


