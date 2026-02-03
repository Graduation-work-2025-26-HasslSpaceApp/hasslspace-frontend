package ru.hse.app.androidApp.ui.components.auth.register

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun RegisterText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 35.sp
) {
    Text(
        text = "Создайте новый \n аккаунт",
        fontWeight = FontWeight.Medium,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onBackground,
        lineHeight = fontSize * 1.03f,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview
@Composable
fun RegisterTextPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        RegisterText()
    }
}

@Preview
@Composable
fun RegisterTextPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        RegisterText()
    }
}