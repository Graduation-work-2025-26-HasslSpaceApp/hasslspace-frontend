package ru.hse.app.hasslspace.ui.components.common.card

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
import ru.hse.app.hasslspace.R
import ru.hse.app.hasslspace.ui.components.common.state.StatusCircle
import ru.hse.app.hasslspace.ui.components.common.text.VariableLight
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.entity.model.StatusPresentation
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun UserCardCallMessage(
    imageLoader: ImageLoader,
    username: String,
    nickname: String,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    status: StatusPresentation = StatusPresentation.INVISIBLE,
    profilePictureUrl: String = "",
    onCardClick: () -> Unit,
//    onCallClick: () -> Unit,
//    onMessageClick: () -> Unit
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

            StatusCircle(
                status = status,
                size = 14.dp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            VariableMedium(
                text = username,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            VariableLight(
                text = "@$nickname",
                fontSize = 12.sp,
                fontColor = MaterialTheme.colorScheme.outline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

//        Row(
//            horizontalArrangement = Arrangement.spacedBy(20.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(start = 5.dp),
//        ) {
//
//            Icon(
//                painter = painterResource(R.drawable.call),
//                contentDescription = "Call",
//                tint = MaterialTheme.colorScheme.onBackground,
//                modifier = Modifier
//                    .size(26.dp)
//                    .clickable { onCallClick() }
//            )
//
//            Icon(
//                painter = painterResource(R.drawable.message),
//                contentDescription = "Message",
//                tint = MaterialTheme.colorScheme.onBackground,
//                modifier = Modifier
//                    .size(26.dp)
//                    .clickable { onMessageClick() }
//            )
//        }
    }
}

@Preview(showBackground = true, name = "All statuses - Light")
@Composable
fun UserCardCallMessagePreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardCallMessage(
                username = "Александр Иванов",
                nickname = "alex_ivanov",
                status = StatusPresentation.ONLINE,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "Мария Петрова",
                nickname = "maria_petrova",
                status = StatusPresentation.INVISIBLE,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "Дмитрий Сидоров",
                nickname = "dmitry_sidorov",
                status = StatusPresentation.DO_NOT_DISTURB,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "Екатерина Волкова",
                nickname = "ekaterina_volkova",
                status = StatusPresentation.INVISIBLE,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "Иван Николаев",
                nickname = "ivan_nikolaev",
                status = StatusPresentation.OFFLINE,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses - Dark")
@Composable
fun UserCardCallMessagePreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardCallMessage(
                username = "Александр Иванов",
                nickname = "alex_ivanov",
                status = StatusPresentation.ONLINE,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "Мария Петрова",
                nickname = "maria_petrova",
                status = StatusPresentation.OFFLINE,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "Дмитрий Сидоров",
                nickname = "dmitry_sidorov",
                status = StatusPresentation.DO_NOT_DISTURB,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "Екатерина Волкова",
                nickname = "ekaterina_volkova",
                status = StatusPresentation.INVISIBLE,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "Иван Николаев",
                nickname = "ivan_nikolaev",
                status = StatusPresentation.INVISIBLE,
                profilePictureUrl = "",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "Default state - Light")
@Composable
fun UserCardCallMessagePreviewDefaultLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardCallMessage(
                username = "Анна Смирнова",
                nickname = "anna_smirnova",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "ДлинноеИмяДлинноеИмяДлинноеИмя",
                nickname = "очень_длинный_никнейм_пользователя",
                profilePictureUrl = "https://example.com/photo.jpg",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "Default state - Dark")
@Composable
fun UserCardCallMessagePreviewDefaultDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardCallMessage(
                username = "Анна Смирнова",
                nickname = "anna_smirnova",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardCallMessage(
                username = "ДлинноеИмяДлинноеИмяДлинноеИмя",
                nickname = "очень_длинный_никнейм_пользователя",
                profilePictureUrl = "https://example.com/photo.jpg",
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}
