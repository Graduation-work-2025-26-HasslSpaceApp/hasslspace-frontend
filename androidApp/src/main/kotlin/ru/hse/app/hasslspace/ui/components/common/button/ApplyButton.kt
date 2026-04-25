package ru.hse.app.hasslspace.ui.components.common.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
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
fun ApplyButton(
    onClick: () -> Unit,
    text: String = "Применить",
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(13.dp),
        colors = colors,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 10.dp,
            end = 16.dp,
            bottom = 10.dp
        ),
        modifier = modifier
            .widthIn(min = 141.dp)
            .height(39.dp)
    ) {
        VariableMedium(
            text = text,
            fontSize = 16.sp,
            fontColor = colors.contentColor
        )
    }
}

@Preview
@Composable
fun ApplyButtonPreview() {
    AppTheme(isDark = false) {
        ApplyButton(
            onClick = {},
        )
    }
}

@Preview
@Composable
fun ApplyButtonPreview1() {
    AppTheme(isDark = false) {
        ApplyButton(
            onClick = {},
            text = "ляляляляляляляляляляляляля"
        )
    }
}

@Preview
@Composable
fun ApplyButtonPreviewDark() {
    AppTheme(isDark = true) {
        ApplyButton(onClick = {})
    }
}