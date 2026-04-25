package ru.hse.app.hasslspace.ui.components.common.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun DeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Пригласить",
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    )
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(13.dp),
        colors = colors,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        VariableMedium(
            text = text,
            fontSize = 16.sp,
            fontColor = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
fun DeleteButtonPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        DeleteButton(
            onClick = {},
        )
    }
}

@Preview
@Composable
fun DeleteButtonPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        DeleteButton(
            onClick = {},
        )
    }
}