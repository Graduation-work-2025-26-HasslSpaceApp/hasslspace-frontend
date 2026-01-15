package ru.hse.app.androidApp.ui.components.settings.systemsettings

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ThemeSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Switch(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
            checkedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            uncheckedThumbColor = MaterialTheme.colorScheme.primary,
            uncheckedTrackColor = MaterialTheme.colorScheme.onPrimary,
            uncheckedBorderColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ThemeSwitchLight() {
    AppTheme(isDark = false) {
        ThemeSwitch(
            checked = true,
            onCheckedChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeSwitchLight1() {
    AppTheme(isDark = false) {
        ThemeSwitch(
            checked = false,
            onCheckedChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeSwitchDark() {
    AppTheme(isDark = true) {
        ThemeSwitch(
            checked = true,
            onCheckedChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeSwitchDark1() {
    AppTheme(isDark = true) {
        ThemeSwitch(
            checked = false,
            onCheckedChange = {}
        )
    }
}