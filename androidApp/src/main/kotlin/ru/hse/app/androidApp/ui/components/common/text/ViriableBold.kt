package ru.hse.app.androidApp.ui.components.common.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun VariableBold(
    text: String,
    fontSize: TextUnit,
    fontColor: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
    ),
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = style,
        fontSize = fontSize,
        color = fontColor,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Preview
@Composable
fun VariableBoldPreview() {
    VariableBold("Текст текст", 11.sp)
}