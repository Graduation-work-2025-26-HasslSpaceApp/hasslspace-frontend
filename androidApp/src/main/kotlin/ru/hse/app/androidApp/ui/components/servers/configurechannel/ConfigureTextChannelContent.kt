package ru.hse.app.androidApp.ui.components.servers.configurechannel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.button.DeleteButton
import ru.hse.app.androidApp.ui.components.common.button.InviteButton
import ru.hse.app.androidApp.ui.components.common.button.PrivateChannelToggle
import ru.hse.app.androidApp.ui.components.common.button.TextArrowButton
import ru.hse.app.androidApp.ui.components.common.field.AuthCustomField
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ConfigureTextChannelContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    channelName: String,
    onChannelNameChanged: (String) -> Unit,
    isDarkTheme: Boolean,
    isPrivate: Boolean,
    onPrivateChange: (Boolean) -> Unit,
    onAddMembers: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteChannel: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackButton(
                onClick = onBackClick
            )
            Spacer(modifier = Modifier.weight(1f))
            InviteButton(
                fontSize = 16.sp,
                onClick = onSaveClick,
                text = "Соханить",
                fontColor = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(15.dp))

        VariableMedium(
            text = "Параметры канала",
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

        Spacer(Modifier.height(25.dp))
        DeleteButton(
            onClick = onDeleteChannel,
            text = "Удалить канал"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigureChannelContentPreviewLightEmpty() {
    AppTheme(isDark = false) {
        ConfigureTextChannelContent(
            onBackClick = {},
            isPrivate = true,
            onPrivateChange = {},
            channelName = "консультация матан",
            onChannelNameChanged = {},
            isDarkTheme = false,
            onSaveClick = {},
            onDeleteChannel = {},
            onAddMembers = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigureChannelContentPreviewLight1() {
    AppTheme(isDark = false) {
        ConfigureTextChannelContent(
            onBackClick = {},
            isPrivate = false,
            onPrivateChange = {},
            channelName = "консультация матан",
            onChannelNameChanged = {},
            isDarkTheme = false,
            onSaveClick = {},
            onDeleteChannel = {},
            onAddMembers = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigureChannelContentPreviewDarkEmpty() {
    AppTheme(isDark = true) {
        ConfigureTextChannelContent(
            onBackClick = {},
            isPrivate = true,
            onPrivateChange = {},
            channelName = "консультация матан",
            onChannelNameChanged = {},
            isDarkTheme = true,
            onSaveClick = {},
            onDeleteChannel = {},
            onAddMembers = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigureChannelContentPreviewDark1() {
    AppTheme(isDark = true) {
        ConfigureTextChannelContent(
            onBackClick = {},
            isPrivate = false,
            onPrivateChange = {},
            channelName = "консультация матан",
            onChannelNameChanged = {},
            isDarkTheme = true,
            onSaveClick = {},
            onDeleteChannel = {},
            onAddMembers = {}
        )
    }
}
