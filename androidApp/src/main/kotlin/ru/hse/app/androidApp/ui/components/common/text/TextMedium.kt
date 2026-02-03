package ru.hse.app.androidApp.ui.components.common.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TextMedium(
    text: String,
    style: TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
    )
) {
    Text(
        text = text,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
fun HeaderMediumPreview() {
    TextMedium("Войти в аккаунт")
}