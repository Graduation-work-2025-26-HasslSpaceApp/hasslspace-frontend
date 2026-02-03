package ru.hse.app.androidApp.ui.components.profile.friends

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.card.UserCardAcceptDismiss
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel

@Composable
fun ApplicationsList(
    imageLoader: ImageLoader,
    isDarkTheme: Boolean,
    friends: List<FriendUiModel>,
    onFriendClick: (FriendUiModel) -> Unit,
    onAcceptClick: (FriendUiModel) -> Unit,
    onDismissClick: (FriendUiModel) -> Unit
) {
    UniversalVerticalGrid(
        items = friends,
        columns = 1
    ) { friend ->
        UserCardAcceptDismiss(
            imageLoader = imageLoader,
            username = friend.name,
            nickname = friend.nickname,
            status = friend.status,
            profilePictureUrl = friend.avatarUrl,
            isDarkTheme = isDarkTheme,
            onCardClick = { onFriendClick(friend) },
            onAcceptClick = { onAcceptClick(friend) },
            onDismissClick = { onDismissClick(friend) }
        )
    }
}
