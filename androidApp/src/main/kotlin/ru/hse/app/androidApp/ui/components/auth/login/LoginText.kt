package ru.hse.app.androidApp.ui.components.auth.login

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
fun LoginText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 35.sp
) {
    Text(
        text = "Войдите \n в существующий \n аккаунт",
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
fun LoginTextPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        LoginText()
    }
}

@Preview
@Composable
fun LoginTextPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        LoginText()
    }
}