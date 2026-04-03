package ru.hse.app.androidApp.screen.voiceroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.theme.AppTheme

private val controlSize = 40.dp
private val controlPadding = 4.dp

@Composable
fun ControlButton(
    painter: Painter,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .size(controlSize)
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(controlPadding),
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
fun ControlButton(
    resourceId: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ControlButton(
        painter = painterResource(id = resourceId),
        contentDescription = contentDescription,
        onClick = onClick,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun ControlButtonPreview() {
    AppTheme(
        isDark = false
    ) {
        ControlButton(
            painter = painterResource(R.drawable.outline_flip_camera_android_24),
            contentDescription = "",
            onClick = {},
        )
    }
}
