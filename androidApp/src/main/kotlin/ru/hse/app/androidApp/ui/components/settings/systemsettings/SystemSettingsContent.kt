package ru.hse.app.androidApp.ui.components.settings.systemsettings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun SystemSettingsContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    isDarkCheck: Boolean,
    isDarkTheme: Boolean
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = if (isDarkTheme) R.drawable.background_up_dark else R.drawable.background_up_light),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .offset(y = (-10).dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BackButton(onClick = onBackClick)

            Spacer(Modifier.height(40.dp))

            VariableBold(
                text = "Системные настройки",
                fontSize = 20.sp,
            )


            ThemeChangeSetting(
                modifier = Modifier.fillMaxWidth(),
                isDarkCheck = isDarkCheck,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Preview(
    name = "System Settings - Theme Toggle",
    showBackground = true,
    widthDp = 360
)
@Composable
fun SystemSettingsContentPreview() {
    val isDarkTheme = remember { mutableStateOf(false) }

    AppTheme(isDark = isDarkTheme.value) {
        SystemSettingsContent(
            modifier = Modifier.fillMaxSize(),
            onBackClick = {},
            isDarkCheck = isDarkTheme.value,
            onCheckedChange = { isDarkTheme.value = it },
            isDarkTheme = isDarkTheme.value
        )
    }
}
