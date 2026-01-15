package ru.hse.app.androidApp.ui.components.profile.addfriens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import ru.hse.app.androidApp.ui.components.common.bar.SearchFriendsToolbar
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.UserCardUndo
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerShortUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun AddFriendsScreenContent(
    imageLoader: ImageLoader,
    requests: List<FriendUiModel>,
    onBackClick: () -> Unit,
    onRequestClick: (FriendUiModel) -> Unit,
    searchText: MutableState<String>,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onUndoClick: (FriendUiModel) -> Unit,
    modifier: Modifier = Modifier,
    infoText: String? = null,
    error: Boolean = false,
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
                text = "Добавить в друзья",
                fontSize = 28.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(15.dp))
        SearchFriendsToolbar(
            modifier = Modifier.fillMaxWidth(),
            searchValue = searchText,
            onValueChange = onValueChange,
            onSendClick = onSendClick,
            infoText = infoText,
            error = error
        )
        Spacer(Modifier.height(15.dp))

        if (requests.isNotEmpty()) {
            VariableLight(
                text = "Отправленные заявки",
                fontSize = 16.sp
            )
            Spacer(Modifier.height(5.dp))
            UniversalVerticalGrid(
                items = requests,
                columns = 1
            ) { request ->
                UserCardUndo(
                    imageLoader = imageLoader,
                    username = request.name,
                    nickname = request.nickname,
                    status = request.status,
                    profilePictureUrl = request.avatarUrl,
                    isDarkTheme = isDarkTheme,
                    onCardClick = { onRequestClick(request) },
                    onUndoClick = { onUndoClick(request) },
                )
            }
        }


    }
}

private fun previewServer(id: String, name: String, count: Int) = ServerShortUiModel(
    id = id,
    name = name,
    avatarUrl = "",
    participantCount = count,
)

private fun previewFriend(id: String, name: String) = FriendUiModel(
    id = id,
    name = name,
    nickname = name.lowercase().replace(" ", "_"),
    avatarUrl = "",
    status = StatusPresentation.ACTIVE
)

@Preview(showBackground = true)
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

    AppTheme(isDark = false) {
        AddFriendsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            requests = friends,
            onBackClick = {},
            onRequestClick = {},
            onSendClick = {},
            onUndoClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false,
            infoText = null,
            error = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsContentPreviewWithRequestsLightOk() {
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

    AppTheme(isDark = false) {
        AddFriendsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            requests = friends,
            onBackClick = {},
            onRequestClick = {},
            onSendClick = {},
            onUndoClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false,
            infoText = "Получилось! Отправили заявку в друзья пользователю @yuulkht",
            error = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsContentPreviewWithRequestsLightError() {
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

    AppTheme(isDark = false) {
        AddFriendsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            requests = friends,
            onBackClick = {},
            onRequestClick = {},
            onSendClick = {},
            onUndoClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false,
            infoText = "Хм... Не получилось. Проверьте, что вы ввели правильное имя пользователя",
            error = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsContentPreviewWithRequestsDark() {
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

    AppTheme(isDark = false) {
        AddFriendsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            requests = friends,
            onBackClick = {},
            onRequestClick = {},
            onSendClick = {},
            onUndoClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false,
            infoText = null,
            error = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsContentPreviewWithRequestsDarkOk() {
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

    AppTheme(isDark = false) {
        AddFriendsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            requests = friends,
            onBackClick = {},
            onRequestClick = {},
            onSendClick = {},
            onUndoClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false,
            infoText = "Получилось! Отправили заявку в друзья пользователю @yuulkht",
            error = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FriendsContentPreviewWithRequestsDarkError() {
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

    AppTheme(isDark = false) {
        AddFriendsScreenContent(
            imageLoader = ImageLoader(LocalContext.current),
            requests = friends,
            onBackClick = {},
            onRequestClick = {},
            onSendClick = {},
            onUndoClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false,
            infoText = "Хм... Не получилось. Проверьте, что вы ввели правильное имя пользователя",
            error = true
        )
    }
}

