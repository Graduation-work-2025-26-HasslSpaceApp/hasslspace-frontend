package ru.hse.app.androidApp.ui.components.settings.usersettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.ui.components.auth.register.UsernameField
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun UsernameSetting(
    editedUsername: MutableState<String>,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onApplyClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        UsernameField(
            text = editedUsername,
            modifier = Modifier.fillMaxWidth()
        )

        AddTextButton(
            onClick = onApplyClick,
            enabled = enabled,
            text = "Применить",
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsernameSettingPreview() {
    val username = remember { mutableStateOf("username") }
    AppTheme(isDark = false) {
        UsernameSetting(
            editedUsername = username,
            enabled = true,
            onApplyClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsernameSettingPreview1() {
    val username = remember { mutableStateOf("username10username10username10") }
    AppTheme(isDark = false) {
        UsernameSetting(
            editedUsername = username,
            enabled = false,
            onApplyClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsernameSettingPreview2() {
    val username = remember { mutableStateOf("username") }
    AppTheme(isDark = true) {
        UsernameSetting(
            editedUsername = username,
            enabled = true,
            onApplyClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UsernameSettingPreview3() {
    val username = remember { mutableStateOf("username") }
    AppTheme(isDark = true) {
        UsernameSetting(
            editedUsername = username,
            enabled = false,
            onApplyClick = {}
        )
    }
}