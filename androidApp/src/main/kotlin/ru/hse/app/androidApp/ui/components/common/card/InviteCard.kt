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
import androidx.compose.material3.ButtonDefaults
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
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun InviteCard(
    imageLoader: ImageLoader,
    name: String,
    expirationDate: LocalDateTime,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    profilePictureUrl: String = "",
    onCancelClick: () -> Unit,
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
                text = name,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            VariableLight(
                text = getExpirationMessage(expirationDate),
                fontSize = 12.sp,
                fontColor = MaterialTheme.colorScheme.outline,
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
                text = "Отменить",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                ),
                onClick = onCancelClick
            )
        }
    }
}

fun getExpirationMessage(expirationDate: LocalDateTime): String {
    val currentDate = LocalDateTime.now()
    val duration = Duration.between(currentDate, expirationDate)
    val daysRemaining = duration.toDays()

    fun getDaySuffix(days: Long): String {
        return when {
            days % 10 == 1L && days % 100 != 11L -> "день"
            days % 10 in 2..4 && (days % 100 !in 12..14) -> "дня"
            else -> "дней"
        }
    }

    return when {
        daysRemaining > 0 -> "Истекает через $daysRemaining ${getDaySuffix(daysRemaining)}"
        daysRemaining == 0L -> "Истекает сегодня"
        else -> "Просрочено на ${-daysRemaining} ${getDaySuffix(-daysRemaining)}"
    }
}

@Preview(showBackground = true, name = "All statuses - Light")
@Composable
fun InviteCardPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            InviteCard(
                name = "Александр Иванов",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "Мария Петрова",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "Дмитрий Сидоров",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "Екатерина Волкова",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "Иван Николаев",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses - Dark")
@Composable
fun InviteCardPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            InviteCard(
                name = "Александр Иванов",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "Мария Петрова",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "Дмитрий Сидоров",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "Екатерина Волкова",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "Иван Николаев",
                profilePictureUrl = "",
                onCancelClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )
        }
    }
}

@Preview(showBackground = true, name = "Default state - Light")
@Composable
fun InviteCardPreviewDefaultLight() {
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            InviteCard(
                name = "Анна Смирнова",
                onCancelClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "ДлинноеИмяДлинноеИмяДлинноеИмя",
                profilePictureUrl = "https://example.com/photo.jpg",
                onCancelClick = {},
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )
        }
    }
}

@Preview(showBackground = true, name = "Default state - Dark")
@Composable
fun InviteCardPreviewDefaultDark() {
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            InviteCard(
                name = "Анна Смирнова",
                onCancelClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                name = "ДлинноеИмяДлинноеИмяДлинноеИмя",
                profilePictureUrl = "https://example.com/photo.jpg",
                onCancelClick = {},
                isDarkTheme = true,
                imageLoader = ImageLoader(LocalContext.current),
                expirationDate = LocalDateTime.now().plusDays(3),
            )
        }
    }
}
