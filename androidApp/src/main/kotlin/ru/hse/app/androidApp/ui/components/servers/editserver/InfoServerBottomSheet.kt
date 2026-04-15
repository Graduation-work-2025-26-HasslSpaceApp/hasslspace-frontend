package ru.hse.app.androidApp.ui.components.servers.editserver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.imageLoader
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.IconTextButton
import ru.hse.app.androidApp.ui.components.common.card.participantsLabel
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.components.settings.usersettings.Exit
import ru.hse.app.androidApp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoServerBottomSheet(
    imageLoader: ImageLoader,
    serverName: String,
    serverAvatarUrl: String,
    countOfMembers: Int,
    onInviteClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onLeaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit,
    onMembersClick: () -> Unit,
    isOwner: Boolean,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.background,
        scrimColor = MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.5f),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = MaterialTheme.colorScheme.outline
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = serverAvatarUrl,
                    imageLoader = imageLoader,
                    error = painterResource(
                        if (isDarkTheme)
                            R.drawable.default_server_photo_dark
                        else
                            R.drawable.default_server_photo_light
                    )
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.height(5.dp))

            VariableMedium(
                text = serverName,
                fontSize = 24.sp
            )

            Box(
                modifier = Modifier
                    .clickable(onClick = onMembersClick)
            ) {
                VariableLight(
                    text = participantsLabel(count = countOfMembers),
                    fontSize = 16.sp
                )
            }


            Spacer(Modifier.height(5.dp))

            if (isOwner) {
                IconTextButton(
                    onClick = onInviteClick,
                    text = "Пригласить",
                    iconResource = if (isDarkTheme) R.drawable.new_friend_dark else R.drawable.new_friend_light
                )

                IconTextButton(
                    onClick = onSettingsClick,
                    text = "Настройки",
                    iconResource = if (isDarkTheme) R.drawable.settings_dark else R.drawable.settings_light
                )

                Spacer(Modifier.height(5.dp))

                Exit(
                    onClick = onDeleteClick,
                    text = "Удалить сервер"
                )
                Spacer(Modifier.height(5.dp))
            } else {
                Exit(
                    onClick = onLeaveClick,
                    text = "Покинуть канал"
                )
                Spacer(Modifier.height(5.dp))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InfoServerBottomSheetPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        InfoServerBottomSheet(
            isDarkTheme = false,
            onDismiss = {},
            imageLoader = LocalContext.current.imageLoader,
            serverName = "Тест сервер",
            countOfMembers = 53,
            serverAvatarUrl = "https://",
            onInviteClick = {},
            onSettingsClick = {},
            onDeleteClick = {},
            onMembersClick = {},
            onLeaveClick = {},
            isOwner = false
        )
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InfoServerBottomSheetPreviewWithRequestsDark() {

    AppTheme(isDark = true) {
        InfoServerBottomSheet(
            isDarkTheme = true,
            onDismiss = {},
            imageLoader = LocalContext.current.imageLoader,
            serverName = "Тест сервер",
            countOfMembers = 53,
            serverAvatarUrl = "https://",
            onInviteClick = {},
            onSettingsClick = {},
            onDeleteClick = {},
            onMembersClick = {},
            onLeaveClick = {},
            isOwner = true
        )
    }
}