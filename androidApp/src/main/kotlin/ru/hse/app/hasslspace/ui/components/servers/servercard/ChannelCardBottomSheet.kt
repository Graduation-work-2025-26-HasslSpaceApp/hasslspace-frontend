package ru.hse.app.hasslspace.ui.components.servers.servercard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.R
import ru.hse.app.hasslspace.ui.components.common.button.IconTextButton
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelCardBottomSheet(
    text: String,
    icon: Int,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
    onReadClick: () -> Unit,
//    onMuteClick: () -> Unit,
    onSetUpChannel: () -> Unit,
    onDismiss: () -> Unit,
    isModerator: Boolean,
    showRead: Boolean = true,
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
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = modifier
                        .size(25.dp)
                )

                Spacer(Modifier.width(20.dp))

                VariableMedium(
                    text = text,
                    fontSize = 24.sp
                )
            }

            Spacer(Modifier.height(5.dp))

            if (showRead) {
                IconTextButton(
                    onClick = onReadClick,
                    text = "Пометить как прочитанное",
                    iconResource = if (isDarkTheme) R.drawable.eye_dark else R.drawable.eye_light
                )
            }

//            IconTextButton(
//                onClick = onMuteClick,
//                text = "Заглушить категорию",
//                iconResource = if (isDarkTheme) R.drawable.mute_dark else R.drawable.mute_light
//            )

            if (isModerator) {
                IconTextButton(
                    onClick = onSetUpChannel,
                    text = "Настроить канал",
                    iconResource = if (isDarkTheme) R.drawable.setup_dark else R.drawable.setup_light
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelCardBottomSheetPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        ChannelCardBottomSheet(
            text = "основной",
            isDarkTheme = false,
            onDismiss = {},
            onReadClick = {},
//            onMuteClick = {},
            onSetUpChannel = {},
            isModerator = true,
            icon = R.drawable.hashtag,
        )
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelCardBottomSheetPreviewWithRequestsDark() {

    AppTheme(isDark = true) {
        ChannelCardBottomSheet(
            text = "Основной",
            isDarkTheme = true,
            onDismiss = {},
            onReadClick = {},
//            onMuteClick = {},
            onSetUpChannel = {},
            isModerator = true,
            icon = R.drawable.voice
        )
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelCardBottomSheetPreviewWithRequestsLight1() {

    AppTheme(isDark = false) {
        ChannelCardBottomSheet(
            text = "основной",
            isDarkTheme = false,
            onDismiss = {},
            onReadClick = {},
//            onMuteClick = {},
            onSetUpChannel = {},
            isModerator = false,
            icon = R.drawable.hashtag,
        )
    }
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChannelCardBottomSheetPreviewWithRequestsDark1() {

    AppTheme(isDark = true) {
        ChannelCardBottomSheet(
            text = "Основной",
            isDarkTheme = true,
            onDismiss = {},
            onReadClick = {},
//            onMuteClick = {},
            onSetUpChannel = {},
            isModerator = false,
            icon = R.drawable.voice
        )
    }
}