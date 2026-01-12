package ru.hse.app.androidApp.ui.components.common.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import ru.hse.app.androidApp.ui.components.common.button.InviteButton
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun UserCardInvite(
    imageLoader: ImageLoader,
    username: String,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    profilePictureUrl: String = "",
    onInviteClick: () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(77.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberAsyncImagePainter(
                model = profilePictureUrl,
                imageLoader = imageLoader,
                error = painterResource(
                    if (isDarkTheme)
                        R.drawable.avatar_default_dark
                    else
                        R.drawable.avatar_default_light
                )
            ),
            contentDescription = null,
            modifier = Modifier
                .size(59.dp)
                .clip(CircleShape)
                .background(Color.LightGray, shape = CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            VariableMedium(
                text = username,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 5.dp),
        ) {

            InviteButton(
                onClick = onInviteClick
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses - Light")
@Composable
fun UserCardInvitePreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardInvite(
                username = "Александр Иванов",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "Мария Петрова",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "Дмитрий Сидоров",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "Екатерина Волкова",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "Иван Николаев",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses - Dark")
@Composable
fun UserCardInvitePreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardInvite(
                username = "Александр Иванов",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "Мария Петрова",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "Дмитрий Сидоров",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "Екатерина Волкова",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "Иван Николаев",
                profilePictureUrl = "",
                onInviteClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "Default state - Light")
@Composable
fun UserCardInvitePreviewDefaultLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardInvite(
                username = "Анна Смирнова",
                onInviteClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "ДлинноеИмяДлинноеИмяДлинноеИмя",
                profilePictureUrl = "https://example.com/photo.jpg",
                onInviteClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "Default state - Dark")
@Composable
fun UserCardInvitePreviewDefaultDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardInvite(
                username = "Анна Смирнова",
                onInviteClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardInvite(
                username = "ДлинноеИмяДлинноеИмяДлинноеИмя",
                profilePictureUrl = "https://example.com/photo.jpg",
                onInviteClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}
