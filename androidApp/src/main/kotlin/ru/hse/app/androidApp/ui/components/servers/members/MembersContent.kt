package ru.hse.app.androidApp.ui.components.servers.members

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.bar.SearchBar
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.UserCardRoleAndArrow
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.RoleMiniUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ServerMembersContent(
    imageLoader: ImageLoader,
    friends: List<ServerMemberUiModel>,
    onBackClick: () -> Unit,
    onFriendClick: (ServerMemberUiModel) -> Unit,
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
            BackButton(onClick = onBackClick)
            Spacer(Modifier.width(10.dp))
            VariableBold(
                text = "Участники",
                fontSize = 28.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(15.dp))
        SearchBar(
            text = searchText,
            onValueChange = onValueChange
        )
        Spacer(Modifier.height(15.dp))

        if (friends.isEmpty()) {
            NoItemsBox("Участников пока нет")
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier,
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(friends, key = { it.id }) { friend ->
                UserCardRoleAndArrow(
                    imageLoader = imageLoader,
                    username = friend.name,
                    nickname = friend.nickname,
                    status = friend.status,
                    profilePictureUrl = friend.avatarUrl,
                    isDarkTheme = isDarkTheme,
                    onArrowClick = { onFriendClick(friend) },
                    role = friend.mainRole?.title,
                    color = friend.mainRole?.color
                )
            }
        }
    }
}

private fun previewServerMember(id: String, name: String, role: RoleMiniUiModel?) =
    ServerMemberUiModel(
        id = id,
        name = name,
        nickname = name.lowercase().replace(" ", "_"),
        avatarUrl = "",
        status = StatusPresentation.ONLINE,
        mainRole = role
    )

private val roles = listOf(
    RoleMiniUiModel("13", "role 1", Color.Blue),
    RoleMiniUiModel("14", "role 2", Color.Red),
    RoleMiniUiModel("15", "role 3", Color.Magenta),
    RoleMiniUiModel("16", "role 4", Color.Yellow),
)

private val friends = listOf(
    previewServerMember("1", "Александр Иванов", null),
    previewServerMember("2", "Мария Петрова", roles[1]),
    previewServerMember("3", "Дмитрий Сидоров", null),
    previewServerMember("4", "Александр Иванов", roles[1]),
    previewServerMember("5", "Мария Петрова", roles[2]),
    previewServerMember("6", "Дмитрий Сидоров", null),
    previewServerMember("7", "Александр Иванов", roles[3]),
    previewServerMember("8", "Мария Петрова", null),
    previewServerMember("9", "Дмитрий Сидоров", roles[0]),
    previewServerMember("10", "Александр Иванов", null),
    previewServerMember("11", "Мария Петрова", roles[3]),
    previewServerMember("12", "Дмитрий Сидоров", null),
)

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun ServerMembersContentPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        ServerMembersContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = friends,
            onBackClick = {},
            onFriendClick = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - Only Friends (Light)")
@Composable
fun ServerMembersContentPreviewOnlyFriendsLight() {

    AppTheme(isDark = true) {
        ServerMembersContent(
            imageLoader = ImageLoader(LocalContext.current),
            friends = friends,
            onBackClick = {},
            onFriendClick = {},
            searchText = "",
            onValueChange = {},
            isDarkTheme = true
        )
    }
}