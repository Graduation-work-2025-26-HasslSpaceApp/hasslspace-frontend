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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.components.common.text.VariableMedium
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun InviteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "Пригласить",
    fontSize: TextUnit = 12.sp,
    fontColor: Color = MaterialTheme.colorScheme.onBackground,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    )
) {
    Button(
        enabled = enabled,
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
            fontSize = fontSize,
            fontColor = fontColor
        )
    }
}

@Preview
@Composable
fun InviteButtonPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        InviteButton(
            onClick = {},
        )
    }
}

@Preview
@Composable
fun InviteButtonPreviewDark() {
    AppTheme(
        isDark = true
    ) {
        InviteButton(
            onClick = {},
        )
    }
}