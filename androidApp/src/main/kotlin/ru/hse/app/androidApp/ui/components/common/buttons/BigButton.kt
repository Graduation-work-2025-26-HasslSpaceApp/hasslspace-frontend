package ru.hse.app.androidApp.ui.components.common.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.ui.components.common.text.TextMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun BigButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(13.dp),
        colors = colors,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 10.dp,
            end = 16.dp,
            bottom = 10.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        TextMedium(text = text)
    }
}

@Preview
@Composable
fun StartButtonPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        BigButton(
            onClick = {},
            text = "Войти в аккаунт"
        )
    }
}

@Preview
@Composable
fun StartButtonPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        BigButton(
            onClick = {},
            text = "Войти в аккаунт"
        )
    }
}