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
import ru.hse.app.androidApp.ui.components.common.state.StatusCircle
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun UserCardThin(
    imageLoader: ImageLoader,
    username: String,
    nickname: String,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    status: StatusPresentation = StatusPresentation.INVISIBLE,
    profilePictureUrl: String = "",
    onCardClick: () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onCardClick)
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
                    .size(37.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            StatusCircle(
                status = status,
                size = 10.dp,
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
    }
}

@Preview(showBackground = true, name = "All statuses with roles - Light")
@Composable
fun UserCardThinPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardThin(
                username = "Александр Иванов",
                nickname = "alex_ivanov",
                status = StatusPresentation.ACTIVE,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Мария Петрова",
                nickname = "maria_petrova",
                status = StatusPresentation.INVISIBLE,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Дмитрий Сидоров",
                nickname = "dmitry_sidorov",
                status = StatusPresentation.DO_NOT_DISTURB,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Екатерина Волкова",
                nickname = "ekaterina_volkova",
                status = StatusPresentation.INVISIBLE,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Иван Николаев",
                nickname = "ivan_nikolaev",
                status = StatusPresentation.NOT_ACTIVE,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses with roles - Dark")
@Composable
fun UserCardThinPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            UserCardThin(
                username = "Александр Иванов",
                nickname = "alex_ivanov",
                status = StatusPresentation.ACTIVE,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Мария Петрова",
                nickname = "maria_petrova",
                status = StatusPresentation.NOT_ACTIVE,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Дмитрий Сидоров",
                nickname = "dmitry_sidorov",
                status = StatusPresentation.DO_NOT_DISTURB,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Екатерина Волкова",
                nickname = "ekaterina_volkova",
                status = StatusPresentation.INVISIBLE,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Иван Николаев",
                nickname = "ivan_nikolaev",
                status = StatusPresentation.INVISIBLE,
                profilePictureUrl = "",
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "Mixed states - Light")
@Composable
fun UserCardThinPreviewMixedLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Карточка с ролью и цветом
            UserCardThin(
                username = "Анна Смирнова",
                nickname = "anna_smirnova",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Карточка без роли (только статус)
            UserCardThin(
                username = "Петр Кузнецов",
                nickname = "petr_kuznetsov",
                status = StatusPresentation.ACTIVE,
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Карточка с длинными текстами
            UserCardThin(
                username = "ДлинноеИмяДлинноеИмяДлинноеИмя",
                nickname = "очень_длинный_никнейм_пользователя",
                profilePictureUrl = "https://example.com/photo.jpg",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Карточка с разными состояниями
            UserCardThin(
                username = "Ольга Соколова",
                nickname = "olga_sokolova",
                status = StatusPresentation.DO_NOT_DISTURB,
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "Mixed states - Dark")
@Composable
fun UserCardThinPreviewMixedDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Карточка с ролью и цветом
            UserCardThin(
                username = "Анна Смирнова",
                nickname = "anna_smirnova",
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Карточка без роли (только статус)
            UserCardThin(
                username = "Петр Кузнецов",
                nickname = "petr_kuznetsov",
                status = StatusPresentation.ACTIVE,
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Карточка с длинными текстами
            UserCardThin(
                username = "ДлинноеИмяДлинноеИмяДлинноеИмя",
                nickname = "очень_длинный_никнейм_пользователя",
                profilePictureUrl = "https://example.com/photo.jpg",
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Карточка с разными состояниями
            UserCardThin(
                username = "Ольга Соколова",
                nickname = "olga_sokolova",
                status = StatusPresentation.DO_NOT_DISTURB,
                onCardClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}

@Preview(showBackground = true, name = "Edge cases - Light")
@Composable
fun UserCardThinPreviewEdgeCasesLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Только цвет без роли
            UserCardThin(
                username = "Иван Иванов",
                nickname = "ivanov",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Только роль без цвета
            UserCardThin(
                username = "Мария Петрова",
                nickname = "petrova",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Алексей",
                nickname = "alex",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )

            Spacer(modifier = Modifier.height(12.dp))

            UserCardThin(
                username = "Тест",
                nickname = "test",
                onCardClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current)
            )
        }
    }
}
