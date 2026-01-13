package ru.hse.app.androidApp.ui.components.common.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ServerCard(
    imageLoader: ImageLoader,
    name: String,
    participantCount: Int,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    serverPictureUrl: String = "",
    onCardClick: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(77.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { onCardClick() }
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        Box {
            Image(
                painter = rememberAsyncImagePainter(
                    model = serverPictureUrl,
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
                    .size(59.dp)
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
                text = name,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            VariableLight(
                text = participantsLabel(count = participantCount),
                fontSize = 10.sp,
                fontColor = MaterialTheme.colorScheme.outline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun participantsLabel(count: Int): String {
    val mod10 = count % 10
    val mod100 = count % 100

    return when {
        mod100 in 11..14 -> "$count участников"
        mod10 == 1 -> "$count участник"
        mod10 in 2..4 -> "$count участника"
        else -> "$count участников"
    }
}


@Preview(showBackground = true, name = "All statuses - Light")
@Composable
fun ServerCardPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            ServerCard(
                name = "Сервер друзей",
                serverPictureUrl = "",
                onCardClick = {},
                participantCount = 53,
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses - Dark")
@Composable
fun ServerCardPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            ServerCard(
                name = "Сервер друзей",
                serverPictureUrl = "",
                onCardClick = {},
                participantCount = 2,
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

