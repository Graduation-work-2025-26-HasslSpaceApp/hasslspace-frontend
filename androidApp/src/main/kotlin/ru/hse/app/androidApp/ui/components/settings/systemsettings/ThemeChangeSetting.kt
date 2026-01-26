package ru.hse.app.androidApp.ui.components.settings.systemsettings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.quarks.AppSwitch
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ThemeChangeSetting(
    modifier: Modifier = Modifier,
    isDarkCheck: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VariableLight(
            text = "Тёмная тема",
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        AppSwitch (
            checked = isDarkCheck,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingLightOffPreview() {
    AppTheme(isDark = false) {
        val isDarkTheme = remember { mutableStateOf(false) }

        ThemeChangeSetting(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isDarkCheck = isDarkTheme.value,
            onCheckedChange = { isDarkTheme.value = it }
        )
    }
}

@Preview(
    name = "Light Theme - Switch On",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingLightOnPreview() {
    AppTheme(isDark = false) {
        val isDarkTheme = remember { mutableStateOf(true) }

        ThemeChangeSetting(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isDarkCheck = isDarkTheme.value,
            onCheckedChange = { isDarkTheme.value = it }
        )
    }
}

@Preview(
    name = "Dark Theme - Switch Off",
    showBackground = true,
    widthDp = 360,
    backgroundColor = 0xFF121212
)
@Composable
fun ThemeChangeSettingDarkOffPreview() {
    AppTheme(isDark = true) {
        val isDarkTheme = remember { mutableStateOf(false) }

        ThemeChangeSetting(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isDarkCheck = isDarkTheme.value,
            onCheckedChange = { isDarkTheme.value = it }
        )
    }
}

@Preview(
    name = "Dark Theme - Switch On",
    showBackground = true,
    widthDp = 360,
    backgroundColor = 0xFF121212
)
@Composable
fun ThemeChangeSettingDarkOnPreview() {
    AppTheme(isDark = true) {
        val isDarkTheme = remember { mutableStateOf(true) }

        ThemeChangeSetting(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isDarkCheck = isDarkTheme.value,
            onCheckedChange = { isDarkTheme.value = it }
        )
    }
}