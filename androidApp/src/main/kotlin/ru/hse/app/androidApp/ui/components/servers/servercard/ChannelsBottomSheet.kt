package ru.hse.app.androidApp.ui.components.servers.servercard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.imageLoader
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.IconTextButton
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelsBottomSheet(
    imageLoader: ImageLoader,
    text: String,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    serverPictureUrl: String = "",
    onReadClick: () -> Unit,
//    onMuteClick: () -> Unit,
    onCreateChannel: () -> Unit,
    onDismiss: () -> Unit,
    isModerator: Boolean
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = serverPictureUrl,
                    imageLoader = imageLoader,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape),
                    error = painterResource(
                        if (isDarkTheme)
                            R.drawable.default_server_photo_dark
                        else
                            R.drawable.default_server_photo_light
                    )
                )

                Spacer(Modifier.width(20.dp))

                VariableMedium(
                    text = text,
                    fontSize = 24.sp
                )
            }

            Spacer(Modifier.height(5.dp))

            IconTextButton(
                onClick = onReadClick,
                text = "Пометить как прочитанное",
                iconResource = if (isDarkTheme) R.drawable.eye_dark else R.drawable.eye_light
            )

//            IconTextButton(
//                onClick = onMuteClick,
//                text = "Заглушить категорию",
//                iconResource = if (isDarkTheme) R.drawable.mute_dark else R.drawable.mute_light
//            )

            if (isModerator) {
                IconTextButton(
                    onClick = onCreateChannel,
                    text = "Создать канал",
                    iconResource = if (isDarkTheme) R.drawable.plus_dark else R.drawable.plus_light
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelsBottomSheetPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        ChannelsBottomSheet(
            text = "Текстовые каналы",
            serverPictureUrl = "",
            imageLoader = LocalContext.current.imageLoader,
            isDarkTheme = false,
            onDismiss = {},
            onReadClick = {},
//            onMuteClick = {},
            onCreateChannel = {},
            isModerator = true
        )
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelsBottomSheetPreviewWithRequestsDark() {

    AppTheme(isDark = true) {
        ChannelsBottomSheet(
            text = "Голосовые каналы",
            serverPictureUrl = "",
            imageLoader = LocalContext.current.imageLoader,
            isDarkTheme = true,
            onDismiss = {},
            onReadClick = {},
//            onMuteClick = {},
            onCreateChannel = {},
            isModerator = true
        )
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelsBottomSheetPreviewWithRequestsLight1() {

    AppTheme(isDark = false) {
        ChannelsBottomSheet(
            text = "Текстовые каналы",
            serverPictureUrl = "",
            imageLoader = LocalContext.current.imageLoader,
            isDarkTheme = false,
            onDismiss = {},
            onReadClick = {},
//            onMuteClick = {},
            onCreateChannel = {},
            isModerator = false
        )
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelsBottomSheetPreviewWithRequestsDark1() {

    AppTheme(isDark = true) {
        ChannelsBottomSheet(
            text = "Голосовые каналы",
            serverPictureUrl = "",
            imageLoader = LocalContext.current.imageLoader,
            isDarkTheme = true,
            onDismiss = {},
            onReadClick = {},
//            onMuteClick = {},
            onCreateChannel = {},
            isModerator = false
        )
    }
}