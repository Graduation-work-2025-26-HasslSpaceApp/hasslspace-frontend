package ru.hse.app.hasslspace.ui.components.common.button

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
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun AddTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Добавить",
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        disabledContainerColor = MaterialTheme.colorScheme.outline,
    )
) {
    Button(
        onClick = onClick,
        enabled = enabled,
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
            fontSize = 12.sp,
            fontColor = if (enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.scrim
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


@Preview
@Composable
fun AddTextButtonPreviewLightDis() {
    AppTheme(
        isDark = false
    ) {
        AddTextButton(
            onClick = {},
            enabled = false
        )
    }
}

@Preview
@Composable
fun AddTextButtonPreviewDarkDis() {
    AppTheme(
        isDark = true
    ) {
        AddTextButton(
            onClick = {},
            enabled = false
        )
    }
}