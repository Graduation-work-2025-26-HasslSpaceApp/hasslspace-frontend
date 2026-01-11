package ru.hse.app.androidApp.ui.components.auth.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.BigButton
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun RegisterScreenContent(
    nickname: MutableState<String>,
    username: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    passwordAgain: MutableState<String>,
    isDarkTheme: Boolean,
    onRegisterClick: () -> Unit,
    onGoToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = if (isDarkTheme) R.drawable.background_up_dark else R.drawable.background_up_light),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .offset(y = (-10).dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            RegisterText(fontSize = 30.sp)
            Spacer(modifier = Modifier.height(20.dp))

            UniqueNicknameField(nickname)
            UsernameField(username)
            EmailField(email)
            Spacer(Modifier.height(20.dp))
            PasswordColumn(
                password = password,
                passwordAgain = passwordAgain
            )

            Spacer(Modifier.height(20.dp))

            BigButton(
                text = "Создать аккаунт",
                onClick = { onRegisterClick() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.scrim
                )
            )
            Spacer(Modifier.height(10.dp))

            Text(
                text = buildAnnotatedString {
                    append("Уже есть аккаунт? ")
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("Войти")
                    }
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onGoToLoginClick() }
            )
        }
    }
}

@Preview
@Composable
fun RegisterScreenContentPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        RegisterScreenContent(
            mutableStateOf("yuulkht"),
            mutableStateOf("Юлия"),
            mutableStateOf("julia@co.com"),
            mutableStateOf("123456789"),
            mutableStateOf("123456789"),
            false,
            {},
            {}
        )
    }
}

@Preview
@Composable
fun RegisterScreenContentPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        RegisterScreenContent(
            mutableStateOf("yuulkht"),
            mutableStateOf("Юлия"),
            mutableStateOf("julia@co.com"),
            mutableStateOf("123456789"),
            mutableStateOf("123456789"),
            true,
            {},
            {}
        )
    }
}