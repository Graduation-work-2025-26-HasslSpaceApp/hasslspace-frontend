package ru.hse.app.androidApp.ui.components.auth.verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun CodeInputRow(
    code: List<String>,
    onDigitChange: (index: Int, value: String) -> Unit
) {
    val focusRequesters = remember { List(code.size) { FocusRequester() } }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        code.forEachIndexed { index, digit ->
            CodeDigitField(
                value = digit,
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                        onDigitChange(index, newValue)

                        if (newValue.isNotEmpty() && index < code.lastIndex)
                            focusRequesters[index + 1].requestFocus()
                        else if (newValue.isEmpty() && index > 0)
                            focusRequesters[index - 1].requestFocus()
                    }
                },
                modifier = Modifier.focusRequester(focusRequesters[index])
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CodeInputRowPreviewLight() {
    AppTheme(isDark = false) {
        CodeInputRow(
            code = listOf("1", "2", "3", "", "", ""),
            onDigitChange = { _, _ -> }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CodeInputRowPreviewDark() {
    AppTheme(isDark = true) {
        CodeInputRow(
            code = listOf("1", "2", "3", "", "", ""),
            onDigitChange = { _, _ -> }
        )
    }
}

