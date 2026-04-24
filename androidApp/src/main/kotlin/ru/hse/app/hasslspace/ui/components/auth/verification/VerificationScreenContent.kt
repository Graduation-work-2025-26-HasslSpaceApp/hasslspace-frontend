package ru.hse.app.hasslspace.ui.components.auth.verification

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.hasslspace.R
import ru.hse.app.hasslspace.ui.components.common.button.BackButton
import ru.hse.app.hasslspace.ui.components.common.button.BigButton
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun VerificationScreenContent(
    code: List<String>,
    onCodeChange: (Int, String) -> Unit,
    onConfirm: () -> Unit,
    onResend: () -> Unit,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean,
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
        Box(modifier = Modifier.padding(top = 50.dp, start = 16.dp, end = 16.dp)) {
            BackButton(
                onClick = onBackClick
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(150.dp))

            VerificationHeader()

            Spacer(Modifier.height(24.dp))

            CodeInputRow(code, onCodeChange)

            Spacer(Modifier.height(16.dp))

            ResendCodeBlock(onResendClick = onResend)

            Spacer(Modifier.height(16.dp))

            BigButton(
                text = "Подтвердить",
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.scrim
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerificationScreenContentPreviewLight() {
    AppTheme(isDark = false) {
        val code = remember { mutableStateListOf("", "", "", "", "", "") }

        VerificationScreenContent(
            code = code,
            onCodeChange = { index, value -> code[index] = value },
            onConfirm = {},
            onResend = {},
            onBackClick = {},
            false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VerificationScreenContentPreviewDark() {
    AppTheme(isDark = true) {
        val code = remember { mutableStateListOf("", "", "", "", "", "") }

        VerificationScreenContent(
            code = code,
            onCodeChange = { index, value -> code[index] = value },
            onConfirm = {},
            onResend = {},
            onBackClick = {},
            true
        )
    }
}

