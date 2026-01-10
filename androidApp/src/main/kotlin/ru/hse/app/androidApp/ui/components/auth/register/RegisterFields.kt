package ru.hse.app.androidApp.ui.components.auth.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.app.androidApp.ui.components.common.field.AuthCustomField
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun UniqueNicknameField(
    text: MutableState<String>,
    modifier: Modifier = Modifier
) {
    AuthCustomField(
        text = text,
        placeholder = "Уникальный, на латинице",
        description = "Никнейм",
        isUniqueUsername = true,
        modifier = modifier
    )
}

@Composable
fun UsernameField(
    text: MutableState<String>,
    modifier: Modifier = Modifier
) {
    AuthCustomField(
        text = text,
        placeholder = "Как вас увидят другие",
        description = "Имя пользователя",
        modifier = modifier
    )
}

@Composable
fun EmailField(
    text: MutableState<String>,
    modifier: Modifier = Modifier
) {
    AuthCustomField(
        text = text,
        placeholder = "Введите адрес электронной почты",
        description = "Почта",
        maxCharacters = null,
        isEmail = true,
        modifier = modifier
    )
}

@Preview
@Composable
fun UniqueNicknameFieldPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        UniqueNicknameField(
            mutableStateOf("hfdjhfsk")
        )
    }
}

@Preview
@Composable
fun UniqueNicknameFieldPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        UniqueNicknameField(
            mutableStateOf("hfdjhfsk")
        )
    }
}

@Preview
@Composable
fun UsernameFieldPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        UsernameField(
            mutableStateOf("Юлия Кухтина")
        )
    }
}

@Preview
@Composable
fun UsernameFieldPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        UsernameField(
            mutableStateOf("Юлия Кухтина")
        )
    }
}

@Preview
@Composable
fun EmailFieldPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        EmailField(
            mutableStateOf("julia@gmail.co")
        )
    }
}

@Preview
@Composable
fun EmailFieldPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        EmailField(
            mutableStateOf("julia@gmail.co")
        )
    }
}