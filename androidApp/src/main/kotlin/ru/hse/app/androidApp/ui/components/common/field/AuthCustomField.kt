package ru.hse.app.androidApp.ui.components.common.field

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun AuthCustomField(
    text: String,
    onTextChanged: (String) -> Unit,
    placeholder: String = "Имя пользователя",
    description: String = "Имя пользователя",
    isEmail: Boolean = false,
    isUniqueUsername: Boolean = false,
    maxCharacters: Int? = 20,
    modifier: Modifier = Modifier
) {
    val isMaxReached = maxCharacters != null && text.length == maxCharacters
    val isEmailValid =
        !isEmail || text.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(text).matches()

    val usernameRegex = Regex("^[a-zA-Z][a-zA-Z0-9_]{2,19}$")
    val usernameError = if (isUniqueUsername) validateUsername(text) else null

    val isError = usernameError != null || !isEmailValid

    Column(
        modifier = modifier
    ) {
        VariableMedium(
            text = description,
            fontSize = 14.sp,
            fontColor = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (maxCharacters == null || it.length <= maxCharacters) {
                    onTextChanged(it)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            },
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (usernameError != null) {
                        Text(
                            text = usernameError,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    if (!isEmailValid) {
                        Text(
                            text = "Некорректный email",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    if (maxCharacters != null) {
                        Text(
                            text = "${text.length} / $maxCharacters",
                            fontSize = 12.sp,
                            color = if (isMaxReached) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            },
            isError = isError,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
        )
    }
}

fun validateUsername(text: String): String? {
    if (text.isEmpty()) return null
    if (text.length < 3) return "Минимум 3 символа"
    if (text.length > 20) return "Максимум 20 символов"
    if (!text.matches(Regex("^[a-zA-Z0-9_]*$")))
        return "Только латиница, цифры и _"
    if (!text.matches(Regex("^[a-zA-Z].*")))
        return "Ник должен начинаться с буквы"
    return null
}


@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewLightCorrect() {
    AppTheme(
        isDark = false
    ) {
        AuthCustomField(
            text = "1@gmail.com",
            onTextChanged = {},
            placeholder = "Почта",
            description = "Почта",
            isEmail = true,
            maxCharacters = 30
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewLightInvalid() {
    AppTheme(
        isDark = false
    ) {
        AuthCustomField(
            text = "1@gmail.com",
            onTextChanged = {},
            placeholder = "Почта",
            description = "Почта",
            isEmail = true,
            maxCharacters = 30
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewLightMaxLength() {
    AppTheme(
        isDark = false
    ) {
        AuthCustomField(
            text = "1@gmail.com",
            onTextChanged = {},

            isEmail = true,
            maxCharacters = 11
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewLightEmpty() {
    AppTheme(
        isDark = false
    ) {
        AuthCustomField(
            text = "",
            onTextChanged = {},
            placeholder = "Почта",
            description = "Почта",
            isEmail = true,
            maxCharacters = 11
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewUsernameLightLess3() {
    AppTheme(
        isDark = false
    ) {
        AuthCustomField(
            text = "a",
            onTextChanged = {},
            isUniqueUsername = true,
            maxCharacters = 30
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewUsernameLightOnlyLat() {
    AppTheme(
        isDark = false
    ) {
        AuthCustomField(
            text = "@@@юл",
            onTextChanged = {},
            isUniqueUsername = true,
            maxCharacters = 30
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewUsernameLightFirstSymbol() {
    AppTheme(
        isDark = false
    ) {
        AuthCustomField(
            text = "1julia",
            onTextChanged = {},
            isUniqueUsername = true,
            maxCharacters = 30
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewUsernameLightFirstSymbolOk() {
    AppTheme(
        isDark = false
    ) {
        AuthCustomField(
            text = "julia_123",
            onTextChanged = {},
            isUniqueUsername = true,
            maxCharacters = 30
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewDarkCorrect() {
    AppTheme(
        isDark = true
    ) {
        AuthCustomField(
            text = "1@gmail.com",
            onTextChanged = {},
            placeholder = "Почта",
            description = "Почта",
            isEmail = true,
            maxCharacters = 30
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewDarkInvalid() {
    AppTheme(
        isDark = true
    ) {
        AuthCustomField(
            text = "1@gmail.com",
            onTextChanged = {},
            placeholder = "Почта",
            description = "Почта",
            isEmail = true,
            maxCharacters = 30
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewDarkMaxLength() {
    AppTheme(
        isDark = true
    ) {
        AuthCustomField(
            text = "1@gmail.com",
            onTextChanged = {},
            placeholder = "Почта",
            description = "Почта",
            isEmail = true,
            maxCharacters = 11
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AuthCustomFieldPreviewDarkEmpty() {
    AppTheme(
        isDark = true
    ) {
        AuthCustomField(
            text = "",
            onTextChanged = {},
            placeholder = "Почта",
            description = "Почта",
            isEmail = true,
            maxCharacters = 11
        )
    }
}
