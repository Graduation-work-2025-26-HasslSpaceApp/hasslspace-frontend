package ru.hse.app.androidApp.ui.components.auth.verification

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun CodeDigitField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .width(48.dp)
            .height(64.dp),
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        ),
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview
@Composable
fun CodeDigitFieldPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        CodeDigitField(
            "6",
            {}
        )
    }
}

@Preview
@Composable
fun CodeDigitFieldPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        CodeDigitField(
            "6",
            {}
        )
    }
}
