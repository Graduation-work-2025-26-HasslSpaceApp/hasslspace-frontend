package ru.hse.app.hasslspace.ui.components.auth.welcome

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun WelcomeTextDescription(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 15.sp
) {
    Text(
        text = "Ваше пространство для общения готово. \n Общайтесь и создавайте свои сообщества. \n Рады вас видеть!",
        fontSize = fontSize,
        fontWeight = FontWeight.Medium,
        lineHeight = fontSize * 1.25f,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview
@Composable
fun WelcomeTextDescriptionPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        WelcomeTextDescription()
    }
}

@Preview
@Composable
fun WelcomeTextDescriptionPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        WelcomeTextDescription()
    }
}