package ru.hse.app.androidApp.ui.components.servers.configurechannel

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.imageLoader
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.RoleCardCheckbox
import ru.hse.app.androidApp.ui.components.common.card.UserCardCheckbox
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ConfigureMembersAndRoles(
    imageLoader: ImageLoader,
    friends: List<FriendCheckboxUiModel>,
    roles: List<RoleMiniCheckboxUiModel>,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onToggleRole: (RoleMiniCheckboxUiModel) -> Unit,
    onToggleFriend: (FriendCheckboxUiModel) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean
) {
    BackHandler {
        onBackClick()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 50.dp)
            .padding(16.dp)
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
                    text = "Настроить участников \n в канале",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }

            AddTextButton(
                text = "Готово",
                onClick = onSaveClick
            )
        }
        Spacer(Modifier.height(15.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier,
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                VariableMedium(
                    text = "Роли",
                    fontSize = 16.sp,
                )
            }

            items(roles, key = { it.id }) { role ->
                RoleCardCheckbox(
                    title = role.title,
                    color = role.color,
                    onClick = { onToggleRole(role) },
                    isChosen = role.isChosen
                )
            }

            item {
                VariableMedium(
                    text = "Участники",
                    fontSize = 16.sp,
                )
            }

            items(friends, key = { it.id }) { friend ->
                UserCardCheckbox(
                    imageLoader = imageLoader,
                    username = friend.name,
                    nickname = friend.nickname,
                    status = friend.status,
                    profilePictureUrl = friend.avatarUrl,
                    isDarkTheme = isDarkTheme,
                    onClick = { onToggleFriend(friend) },
                    isChosen = friend.isChosen
                )
            }
        }
    }
}

private fun previewFriend(id: String, name: String, isChosen: Boolean) = FriendCheckboxUiModel(
    id = id,
    name = name,
    nickname = name.lowercase().replace(" ", "_"),
    avatarUrl = "",
    status = StatusPresentation.ACTIVE,
    isChosen = isChosen
)


@Preview(showBackground = true)
@Composable
fun ConfigureMembersContentPreviewLightEmpty() {
    val friends = remember {
        mutableStateListOf(
            previewFriend("1", "Александр Иванов", false),
            previewFriend("2", "Мария Петрова", false),
            previewFriend("3", "Дмитрий Сидоров", true),
            previewFriend("4", "Александр Иванов", true),
            previewFriend("5", "Мария Петрова", false),
            previewFriend("6", "Дмитрий Сидоров", false),
            previewFriend("7", "Александр Иванов", false),
            previewFriend("8", "Мария Петрова", false),
            previewFriend("9", "Дмитрий Сидоров", true),
            previewFriend("10", "Александр Иванов", false),
            previewFriend("11", "Мария Петрова", false),
            previewFriend("12", "Дмитрий Сидоров", false),
        )
    }

    val roles = remember {
        mutableStateListOf(
            RoleMiniCheckboxUiModel("13", "role 1", Color.Blue, isChosen = false),
            RoleMiniCheckboxUiModel("14", "role 2", Color.Red, isChosen = true),
            RoleMiniCheckboxUiModel("15", "role 3", Color.Magenta, isChosen = true),
            RoleMiniCheckboxUiModel("16", "role 4", Color.Yellow, isChosen = false),
        )
    }
    AppTheme(isDark = false) {
        ConfigureMembersAndRoles(
            imageLoader = LocalContext.current.imageLoader,
            friends = friends,
            roles = roles,
            onBackClick = {},
            onSaveClick = {},
            onToggleRole = { clickedRole ->
                // НАХОДИМ ИНДЕКС и ЗАМЕНЯЕМ ЭЛЕМЕНТ
                val index = roles.indexOfFirst { it.id == clickedRole.id }
                if (index != -1) {
                    val updatedRole = roles[index].copy(
                        isChosen = !roles[index].isChosen
                    )
                    roles[index] = updatedRole // ЭТО ВЫЗЫВАЕТ РЕКОМПОЗИЦИЮ!
                }
            },
            onToggleFriend = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigureMembersContentPreviewDarkEmpty() {
    val friends = mutableStateListOf(
        previewFriend("1", "Александр Иванов", false),
        previewFriend("2", "Мария Петрова", false),
        previewFriend("3", "Дмитрий Сидоров", true),
        previewFriend("4", "Александр Иванов", true),
        previewFriend("5", "Мария Петрова", false),
        previewFriend("6", "Дмитрий Сидоров", false),
        previewFriend("7", "Александр Иванов", false),
        previewFriend("8", "Мария Петрова", false),
        previewFriend("9", "Дмитрий Сидоров", true),
        previewFriend("10", "Александр Иванов", false),
        previewFriend("11", "Мария Петрова", false),
        previewFriend("12", "Дмитрий Сидоров", false),
    )

    val roles = mutableStateListOf(
        RoleMiniCheckboxUiModel("13", "role 1", Color.Blue, isChosen = false),
        RoleMiniCheckboxUiModel("14", "role 2", Color.Red, isChosen = true),
        RoleMiniCheckboxUiModel("15", "role 3", Color.Magenta, isChosen = true),
        RoleMiniCheckboxUiModel("16", "role 4", Color.Yellow, isChosen = false),
    )
    AppTheme(isDark = true) {
        ConfigureMembersAndRoles(
            imageLoader = LocalContext.current.imageLoader,
            friends = friends,
            roles = roles,
            onBackClick = {},
            onSaveClick = {},
            onToggleRole = {},
            onToggleFriend = {},
            isDarkTheme = true
        )
    }
}
