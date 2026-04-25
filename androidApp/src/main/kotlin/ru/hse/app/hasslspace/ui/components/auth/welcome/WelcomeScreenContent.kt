package ru.hse.app.hasslspace.ui.components.auth.welcome

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.hasslspace.R
import ru.hse.app.hasslspace.ui.components.common.button.BigButton
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun WelcomeScreenContent(
    isDarkTheme: Boolean,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
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
        Image(
            painter = painterResource(id = if (isDarkTheme) R.drawable.background_down_dark else R.drawable.background_down_light),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .graphicsLayer(
                    scaleX = 3f,
                    scaleY = 3f,
                    clip = false
                )
                .offset(y = (-30).dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 50.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BigButton(
                onClick = onLoginClick,
                text = "Войти в аккаунт",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            BigButton(
                onClick = onRegisterClick,
                text = "Зарегистрироваться",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 160.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            WelcomeText()

            Spacer(modifier = Modifier.height(16.dp))

            WelcomeTextDescription()
        }
    }
}

@Preview
@Composable
fun WelcomeScreenContentPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        WelcomeScreenContent(
            isDarkTheme = false,
            {},
            {}
        )
    }
}

@Preview
@Composable
fun WelcomeScreenContentPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        WelcomeScreenContent(
            isDarkTheme = true,
            {},
            {}
        )
    }
}