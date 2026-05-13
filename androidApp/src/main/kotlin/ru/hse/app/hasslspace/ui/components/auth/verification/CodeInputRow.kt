package ru.hse.app.hasslspace.ui.components.auth.verification

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun CodeInputRow(
    code: List<String>,
    onDigitChange: (index: Int, value: String) -> Unit,
    onCodeComplete: (() -> Unit)? = null
) {
    val focusRequesters = remember { List(code.size) { FocusRequester() } }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        code.forEachIndexed { index, digit ->
            CodeDigitField(
                value = digit,
                onValueChange = { newValue ->
                    val digitInput = newValue.firstOrNull { it.isDigit() }?.toString() ?: ""

                    when {
                        digitInput.isNotEmpty() -> {
                            onDigitChange(index, digitInput)
                            if (index < code.lastIndex) {
                                focusRequesters[index + 1].requestFocus()
                            } else {
                                if (code.dropLast(1).all { it.isNotEmpty() }) {
                                    onCodeComplete?.invoke()
                                }
                            }
                        }

                        digit.isNotEmpty() && newValue.isEmpty() -> {
                            onDigitChange(index, "")
                        }
                    }
                },
                onBackspace = {
                    if (index > 0) {
                        onDigitChange(index - 1, "")
                        focusRequesters[index - 1].requestFocus()
                    }
                },
                modifier = Modifier.focusRequester(focusRequesters[index])
            )
        }
    }

    LaunchedEffect(Unit) {
        val firstEmpty = code.indexOfFirst { it.isEmpty() }.takeIf { it >= 0 } ?: 0
        focusRequesters[firstEmpty].requestFocus()
    }
}

@Preview(showBackground = true, name = "Light - Interactive")
@Composable
fun CodeInputRowPreviewLight() {
    AppTheme(isDark = false) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var code by remember { mutableStateOf(List(6) { "" }) }
            CodeInputRow(
                code = code,
                onDigitChange = { index, value ->
                    code = code.toMutableList().apply { this[index] = value }
                },
                onCodeComplete = {
                    android.util.Log.d("CodeInput", "Code complete: ${code.joinToString("")}")
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Dark - Interactive",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CodeInputRowPreviewDark() {
    AppTheme(isDark = true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var code by remember { mutableStateOf(List(6) { "" }) }
            CodeInputRow(
                code = code,
                onDigitChange = { index, value ->
                    code = code.toMutableList().apply { this[index] = value }
                }
            )
        }
    }
}

@Preview(name = "Filled state")
@Composable
fun CodeInputRowPreviewFilled() {
    AppTheme(isDark = false) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CodeInputRow(
                code = listOf("7", "3", "9", "1", "2", ""),
                onDigitChange = { _, _ -> }
            )
        }
    }
}

@Preview(name = "Error state")
@Composable
fun CodeInputRowPreviewError() {
    AppTheme(isDark = false) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CodeInputRow(
                code = listOf("1", "2", "X", "", "", ""),
                onDigitChange = { _, _ -> }
            )
        }
    }
}

