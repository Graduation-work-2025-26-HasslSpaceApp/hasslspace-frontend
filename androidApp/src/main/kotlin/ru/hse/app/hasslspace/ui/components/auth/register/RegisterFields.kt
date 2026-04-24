package ru.hse.app.hasslspace.ui.components.auth.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.hse.app.hasslspace.ui.components.common.field.AuthCustomField
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun UniqueNicknameField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AuthCustomField(
        text = text,
        onTextChanged = onTextChanged,
        placeholder = "Уникальный, на латинице",
        description = "Никнейм",
        isUniqueUsername = true,
        modifier = modifier
    )
}

@Composable
fun UsernameField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AuthCustomField(
        text = text,
        onTextChanged = onTextChanged,
        placeholder = "Как вас увидят другие",
        description = "Имя пользователя",
        maxCharacters = 30,
        modifier = modifier
    )
}

@Composable
fun EmailField(
    text: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AuthCustomField(
        text = text,
        onTextChanged = onTextChanged,
        placeholder = "Адрес электронной почты",
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
            "fsdfsdf", {}
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
            "fsdfsdf", {}
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
            "Юлия Кухтина", {}
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
            "Юлия Кухтина", {}
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
            "julia@gmail.co", {}
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
            "julia@gmail.co", {}
        )
    }
}