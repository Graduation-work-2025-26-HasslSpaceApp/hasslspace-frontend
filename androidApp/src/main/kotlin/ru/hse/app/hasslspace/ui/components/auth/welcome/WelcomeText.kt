package ru.hse.app.hasslspace.ui.components.auth.welcome

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.theme.AppTheme
import ru.hse.app.hasslspace.ui.theme.RighteousFontFamily

@Composable
fun WelcomeText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 35.sp
) {
    Text(
        text = buildAnnotatedString {
            append("Добро пожаловать \n в ")
            withStyle(style = SpanStyle(fontFamily = RighteousFontFamily)) {
                append("HasslSpace")
            }
        },
        fontWeight = FontWeight.Medium,
        fontSize = fontSize,
        color = MaterialTheme.colorScheme.onBackground,
        lineHeight = fontSize * 1.34f,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview
@Composable
fun WelcomeTextPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        WelcomeText()
    }
}

@Preview
@Composable
fun WelcomeTextPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        WelcomeText()
    }
}