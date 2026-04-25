package ru.hse.app.hasslspace.ui.components.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun AlreadyHaveAccountButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            append("Уже есть аккаунт? ")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append("Войти")
            }
        },
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = modifier.clickable { onClick() }
    )
}

@Preview
@Composable
fun AlreadyHaveAccountButtonPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        AlreadyHaveAccountButton({})
    }
}

@Preview
@Composable
fun AlreadyHaveAccountButtonPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        AlreadyHaveAccountButton({})
    }
}