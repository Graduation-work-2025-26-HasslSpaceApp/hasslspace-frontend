package ru.hse.app.androidApp.ui.components.common.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun CloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .size(40.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.close),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview
@Composable
fun CloseButtonPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        CloseButton(
            onClick = {},
        )
    }
}

@Preview
@Composable
fun CloseButtonPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        CloseButton(
            onClick = {},
        )
    }
}