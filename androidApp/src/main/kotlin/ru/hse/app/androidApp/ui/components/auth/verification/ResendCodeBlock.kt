package ru.hse.app.androidApp.ui.components.auth.verification

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium

@Composable
fun ResendCodeBlock(
    cooldownSeconds: Int = 10,
    onResendClick: () -> Unit
) {
    var secondsLeft by remember { mutableStateOf(cooldownSeconds) }
    var canResend by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }
        canResend = true
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        VariableMedium("Не получили код?", fontSize = 15.sp)
        Spacer(modifier = Modifier.width(2.dp))

        TextButton(
            onClick = {
                if (canResend) {
                    onResendClick()
                    secondsLeft = cooldownSeconds
                    canResend = false
                    coroutineScope.launch {
                        while (secondsLeft > 0) {
                            delay(1000)
                            secondsLeft--
                        }
                        canResend = true
                    }
                }
            },
            enabled = canResend
        ) {
            VariableMedium(
                text = if (canResend) "Отправить повторно" else "Повторно через $secondsLeft с",
                fontColor = if (canResend) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                fontSize = 15.sp
            )
        }
    }
}