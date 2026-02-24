package ru.hse.app.androidApp.ui.components.auth.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.field.PasswordCustomField

@Composable
fun PasswordColumn(
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordAgain: String,
    onPasswordAgainChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box() {
        Column(
            modifier = modifier
        ) {
            PasswordCustomField(
                text = password,
                onStringChanged = onPasswordChanged,
                shouldBeChecked = true
            )
            PasswordCustomField(
                text = passwordAgain,
                onStringChanged = onPasswordAgainChanged,
                placeholder = "Повторите пароль",
                description = "Повторите пароль"
            )
        }
        if (password != passwordAgain) {
            Text(
                text = "Пароли должны совпадать",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(bottom = 0.dp)
                    .align(Alignment.BottomStart)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PasswordColumnPreview() {
    PasswordColumn(
        password = "",
        {},
        passwordAgain = "",
        {}
    )
}