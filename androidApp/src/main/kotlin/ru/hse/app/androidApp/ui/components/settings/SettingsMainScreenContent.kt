package ru.hse.app.androidApp.ui.components.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.button.AddButtonNoPicture
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun SettingsMainScreenContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onUserSettingsClick: () -> Unit,
    onSystemSettingsClick: () -> Unit
) {
    val context = LocalContext.current

    fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BackButton(onClick = onBackClick)

        Spacer(Modifier.height(10.dp))

        VariableBold(
            text = "Настройки",
            fontSize = 28.sp,
        )

        Spacer(Modifier.height(10.dp))

        AddButtonNoPicture(
            text = "Пользовательские",
            onClick = onUserSettingsClick
        )

        AddButtonNoPicture(
            text = "Системные",
            onClick = onSystemSettingsClick
        )

        Spacer(Modifier.height(15.dp))
        VariableMedium(text = "Связаться с разработчиками", fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))
        ClickableTextLink(
            text = "yuekukhtina@edu.hse.ru",
            url = "mailto:yuekukhtina@edu.hse.ru",
            onClick = ::openLink
        )
        ClickableTextLink(
            text = "aevsyukov_1@edu.hse.ru",
            url = "mailto:aevsyukov_1@edu.hse.ru",
            onClick = ::openLink
        )
        Spacer(Modifier.height(18.dp))
        VariableMedium(text = "Задать вопросы о приложении", fontSize = 20.sp)
        Spacer(Modifier.height(8.dp))
        //TODO добавить актуальную почту
        ClickableTextLink(
            text = "НАША ПОЧТА",
            url = "mailto:godaily.appnotification@gmail.com",
            onClick = ::openLink
        )
    }
}

@Composable
fun ClickableTextLink(text: String, url: String, onClick: (String) -> Unit) {
    val annotatedString = buildAnnotatedString {
        pushStyle(
            SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                fontSize = 15.sp
            )
        )
        append(text)
        addStringAnnotation(tag = "URL", annotation = url, start = 0, end = text.length)
        pop()
    }

    BasicText(
        text = annotatedString,
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick(url) }
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsMainScreenContentLightPreview() {
    AppTheme(isDark = false) {
        SettingsMainScreenContent(
            onBackClick = {},
            onUserSettingsClick = {},
            onSystemSettingsClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsMainScreenContentDarkPreview() {
    AppTheme(isDark = true) {
        SettingsMainScreenContent(
            onBackClick = {},
            onUserSettingsClick = {},
            onSystemSettingsClick = {}
        )
    }
}