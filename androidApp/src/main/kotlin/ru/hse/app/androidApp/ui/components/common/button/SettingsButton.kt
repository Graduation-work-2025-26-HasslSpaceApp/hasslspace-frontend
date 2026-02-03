package ru.hse.app.androidApp.ui.components.common.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.R

@Composable
fun SettingsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean
) {
    Icon(
        painter = painterResource(id = R.drawable.settings),
        contentDescription = "Settings",
        tint = if (isDarkTheme) Color.Black else Color.White,
        modifier = modifier
            .size(32.dp)
            .clickable { onClick() }
    )
}
