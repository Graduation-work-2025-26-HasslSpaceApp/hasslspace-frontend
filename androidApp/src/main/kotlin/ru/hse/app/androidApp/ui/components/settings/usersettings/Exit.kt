package ru.hse.app.androidApp.ui.components.settings.usersettings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium

@Composable
fun Exit(
    onClick: () -> Unit,
    text: String = "Выйти из приложения",
    contentColor: Color = MaterialTheme.colorScheme.error
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        modifier = Modifier
            .height(23.dp)
    ) {
        VariableMedium(text, 15.sp, contentColor)
    }
}

@Preview
@Composable
fun ExitPreview() {
    Exit(onClick = {})
}