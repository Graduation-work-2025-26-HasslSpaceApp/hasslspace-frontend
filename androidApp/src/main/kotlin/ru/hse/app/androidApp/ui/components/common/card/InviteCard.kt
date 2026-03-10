package ru.hse.app.androidApp.ui.components.common.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import ru.hse.app.androidApp.ui.entity.model.InvitationUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme
import java.time.Duration
import java.time.LocalDateTime

@Composable
fun InviteCard(
    link: String,
    expirationDate: LocalDateTime,
    modifier: Modifier = Modifier,
    onInvitationClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onInvitationClick)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            VariableMedium(
                text = link,
                fontSize = 14.sp,
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
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
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
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
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
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjeijjfopishcowihecopwheofhosdhaoasoasodasdha",
                onCancelClick = {},
                onInvitationClick = {},
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
                link = "https://hasslspace.ru/adajsdlkjei",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )

            Spacer(modifier = Modifier.height(12.dp))

            InviteCard(
                link = "https://hasslspace.ru/adajsdlkjeijjfopishcowihecopwheofhosdhaoasoasodasdha",
                onCancelClick = {},
                onInvitationClick = {},
                expirationDate = LocalDateTime.now().plusDays(3),
            )
        }
    }
}
