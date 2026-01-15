package ru.hse.app.androidApp.ui.components.userinfocard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun UserDescription(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        modifier = modifier
    ) {
        VariableMedium(
            text = "Обо мне",
            fontSize = 14.sp
        )
        Spacer(Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLow)
                .padding(vertical = 16.dp, horizontal = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            VariableLight(
                text = text,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDescriptionPreview() {
    AppTheme(isDark = false) {
        UserDescription(
            text = "Сообщение щрур фыдгргуар фыщршгцра фыщшаргшар фщыращшра фшыарфс фыгрсфрцу ащцшуарщрс сщцрацрры сщыарщхыарщыс ы уашщцарцщыуврсщырс ыу щцоаыв сщыш ащущцасшы",
        )
    }
}