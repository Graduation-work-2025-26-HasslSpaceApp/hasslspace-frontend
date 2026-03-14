package ru.hse.app.androidApp.ui.components.common.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ChatCard(
    imageLoader: ImageLoader,
    title: String,
    lastMessage: String,
    timeOfLastMessage: String,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    chatPictureUrl: String = "",
    unreadCount: Int,
    onChatClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.Transparent)
            .clickable { onChatClick() }, verticalAlignment = Alignment.CenterVertically
    ) {

        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = chatPictureUrl,
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
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            VariableMedium(
                text = title,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            VariableLight(
                text = lastMessage,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier
                .align(Alignment.Top),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.End
        ) {
            VariableLight(
                text = timeOfLastMessage,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (unreadCount > 0) {
                BadgeCount(count = unreadCount)
            }
        }
    }
}

@Composable
private fun BadgeCount(
    count: Int,
    modifier: Modifier = Modifier
) {
    val displayCount = if (count > 99) "99+" else count.toString()

    Badge(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        VariableMedium(
            text = displayCount,
            fontColor = MaterialTheme.colorScheme.onPrimary,
            fontSize = 12.sp,
            maxLines = 1
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ChatCardPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            ChatCard(
                title = "Марина Ландышева",
                chatPictureUrl = "",
                onChatClick = {},
                lastMessage = "Вы: пришли мне, пожалуйста, последнюю картинку",
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                timeOfLastMessage = "15:16",
                unreadCount = 101
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatCardPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            ChatCard(
                title = "Марина Ландышева",
                chatPictureUrl = "",
                onChatClick = {},
                lastMessage = "Вы: пришли мне, пожалуйста, последнюю картинку",
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current),
                timeOfLastMessage = "15:16",
                unreadCount = 52
            )
        }
    }
}

