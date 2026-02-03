package ru.hse.app.androidApp.ui.components.profile.friends

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.card.UserCardCallMessage
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel

@Composable
fun FriendsList(
    contentPadding: PaddingValues = PaddingValues(16.dp),
    imageLoader: ImageLoader,
    isDarkTheme: Boolean,
    friends: List<FriendUiModel>,
    onFriendClick: (FriendUiModel) -> Unit,
    onCallClick: (FriendUiModel) -> Unit,
    onMessageClick: (FriendUiModel) -> Unit
) {
    UniversalVerticalGrid(
        items = friends,
        columns = 1,
        contentPadding = contentPadding,
    ) { friend ->
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
