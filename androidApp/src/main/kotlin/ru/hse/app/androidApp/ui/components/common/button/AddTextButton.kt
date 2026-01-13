package ru.hse.app.androidApp.ui.components.common.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
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
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun AddTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Добавить",
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary
    )
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(13.dp),
        colors = colors,
        contentPadding = PaddingValues(
            start = 10.dp,
            top = 5.dp,
            end = 10.dp,
            bottom = 5.dp
        ),
        modifier = modifier
            .height(26.dp)
    ) {
        VariableMedium(
            text = text,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun AddTextButtonPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        AddTextButton(
            onClick = {},
        )
    }
}

@Preview
@Composable
fun AddTextButtonPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        AddTextButton(
            onClick = {},
        )
    }
}