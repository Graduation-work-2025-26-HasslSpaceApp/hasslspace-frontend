package ru.hse.app.androidApp.ui.components.common.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun VariableLight(
    text: String,
    fontSize: TextUnit,
    fontColor: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 15.sp,
    ),
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = style,
        fontSize = fontSize,
        color = fontColor,
        modifier = modifier
    )
}

@Preview
@Composable
fun VariableLightPreview() {
    VariableLight("Текст текст", 11.sp)
}