package ru.hse.app.androidApp.ui.components.servers.createchannel

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.BigButton
import ru.hse.app.androidApp.ui.components.common.button.CloseButton
import ru.hse.app.androidApp.ui.components.common.button.PrivateChannelToggle
import ru.hse.app.androidApp.ui.components.common.button.TextArrowButton
import ru.hse.app.androidApp.ui.components.common.field.AuthCustomField
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.components.servers.configurechannel.UserLimitSlider
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun CreateChannelContent(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    channelName: String,
    type: String,
    onChannelNameChanged: (String) -> Unit,
    onCreateChannelClick: () -> Unit,
    isDarkTheme: Boolean,
    isPrivate: Boolean,
    onPrivateChange: (Boolean) -> Unit,
    onAddMembers: () -> Unit,
    sliderValue: Float,
    onSliderValueChange: (Float) -> Unit
) {
    BackHandler {
        onCloseClick()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .padding(16.dp)
    ) {
        CloseButton(onClick = onCloseClick)
        Spacer(Modifier.height(15.dp))

        VariableMedium(
            text = if (type == "TEXT") "Создать текстовый канал" else "Создать голосовой канал",
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )

        Spacer(Modifier.height(25.dp))

        AuthCustomField(
            text = channelName,
            onTextChanged = onChannelNameChanged,
            placeholder = "Новый канал",
            description = "Название канала",
            maxCharacters = 30,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(25.dp))

        PrivateChannelToggle(
            iconResource = if (isDarkTheme) R.drawable.lock_dark else R.drawable.lock_light,
            checked = isPrivate,
            onCheckedChange = onPrivateChange
        )

        if (isPrivate) {
            Spacer(Modifier.height(25.dp))
            TextArrowButton(
                onClick = onAddMembers,
                text = "Настроить участников или роли"
            )
        }
        if (type != "TEXT") {
            Spacer(Modifier.height(30.dp))
            UserLimitSlider(
                sliderValue = sliderValue,
                onSliderValueChange = onSliderValueChange
            )
        }

        Spacer(Modifier.weight(1f))

        BigButton(
            onClick = onCreateChannelClick,
            text = "Создать"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateChannelContentPreviewLightEmpty() {
    AppTheme(isDark = false) {
        CreateChannelContent(
            onCloseClick = {},
            isPrivate = true,
            onPrivateChange = {},
            channelName = "консультация матан",
            onChannelNameChanged = {},
            onCreateChannelClick = {},
            isDarkTheme = false,
            onAddMembers = {},
            type = "TEXT",
            sliderValue = 25f,
            onSliderValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateChannelContentPreviewLight1() {
    AppTheme(isDark = false) {
        CreateChannelContent(
            onCloseClick = {},
            isPrivate = false,
            onPrivateChange = {},
            channelName = "консультация матан",
            onChannelNameChanged = {},
            onCreateChannelClick = {},
            isDarkTheme = false,
            onAddMembers = {},
            type = "VOICE",
            sliderValue = 25f,
            onSliderValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateChannelContentPreviewDarkEmpty() {
    AppTheme(isDark = true) {
        CreateChannelContent(
            onCloseClick = {},
            isPrivate = true,
            onPrivateChange = {},
            channelName = "консультация матан",
            onChannelNameChanged = {},
            onCreateChannelClick = {},
            isDarkTheme = true,
            onAddMembers = {},
            type = "voice",
            sliderValue = 25f,
            onSliderValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateChannelContentPreviewDark1() {
    AppTheme(isDark = true) {
        CreateChannelContent(
            onCloseClick = {},
            isPrivate = false,
            onPrivateChange = {},
            channelName = "консультация матан",
            onChannelNameChanged = {},
            onCreateChannelClick = {},
            isDarkTheme = true,
            onAddMembers = {},
            type = "text",
            sliderValue = 25f,
            onSliderValueChange = {}
        )
    }
}
