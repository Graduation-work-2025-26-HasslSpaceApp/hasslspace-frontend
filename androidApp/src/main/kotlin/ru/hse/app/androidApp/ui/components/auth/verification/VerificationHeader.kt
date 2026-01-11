package ru.hse.app.androidApp.ui.components.auth.verification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun VerificationHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        VariableBold(
            text = "Введите код подтверждения",
            fontSize = 35.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))
        VariableMedium(
            text = "Введите код подтверждения, отправленный на вашу электронную почту",
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))
        VariableMedium(
            text = "Проверьте папку «Спам»",
            fontSize = 15.sp,
            fontColor = MaterialTheme.colorScheme.outline,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun VerificationHeaderPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        VerificationHeader()
    }
}

@Preview
@Composable
fun VerificationHeaderPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        VerificationHeader()
    }
}
