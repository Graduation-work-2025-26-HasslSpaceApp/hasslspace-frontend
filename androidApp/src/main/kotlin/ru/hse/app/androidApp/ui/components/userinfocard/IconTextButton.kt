package ru.hse.app.androidApp.ui.components.userinfocard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    iconResource: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        modifier.clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(iconResource),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.LightGray, shape = CircleShape),
            contentScale = ContentScale.Crop
        )
        VariableLight(
            text = text,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IconTextButtonPreview() {
    AppTheme(isDark = false) {
        IconTextButton(
            text = "Сообщение",
            iconResource = R.drawable.option_call,
            onClick = {}
        )
    }
}