package ru.hse.app.androidApp.ui.components.servers.members

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.imageLoader
import ru.hse.app.androidApp.ui.components.common.card.UserCardInvite
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.FriendUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMembersSheet(
    imageLoader: ImageLoader,
    friends: List<FriendUiModel>,
    onInviteClick: (FriendUiModel) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = MaterialTheme.colorScheme.outline
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            VariableBold(
                text = "Пригласите друга на сервер",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )

            UniversalVerticalGrid(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = friends,
                columns = 1
            ) { friend ->
                UserCardInvite(
                    imageLoader = imageLoader,
                    username = friend.name,
                    profilePictureUrl = friend.avatarUrl,
                    isDarkTheme = isDarkTheme,
                    onInviteClick = { onInviteClick(friend) },
                )
            }
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

private val friends = listOf(
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

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun AddMembersSheetContentPreviewLight() {

    AppTheme(isDark = false) {
        AddMembersSheet(
            imageLoader = LocalContext.current.imageLoader,
            friends = friends,
            onInviteClick = {},
            isDarkTheme = false,
            onDismiss = {}
        )
    }
}