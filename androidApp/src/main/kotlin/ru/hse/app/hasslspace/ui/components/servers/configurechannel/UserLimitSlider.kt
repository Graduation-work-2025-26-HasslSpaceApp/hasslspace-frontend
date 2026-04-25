package ru.hse.app.hasslspace.ui.components.servers.configurechannel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.hasslspace.ui.components.common.text.VariableLight
import ru.hse.app.hasslspace.ui.theme.AppTheme
import kotlin.math.roundToInt

@Composable
fun UserLimitSlider(
    modifier: Modifier = Modifier,
    sliderValue: Float,
    onSliderValueChange: (Float) -> Unit
) {

    val roundedValue = sliderValue.roundToInt()

    val limitText = if (roundedValue == 0) {
        "Без ограничений"
    } else {
        roundedValue.toString()
    }

    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            ) {
            VariableLight(
                modifier = Modifier.weight(1f),
                text = "Лимит пользователей",
                fontSize = 16.sp,
                fontColor = MaterialTheme.colorScheme.onBackground
            )

            VariableLight(
                text = limitText, fontSize = 16.sp, fontColor = MaterialTheme.colorScheme.onSurface
            )

        }

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = sliderValue,
            onValueChange = onSliderValueChange,
            valueRange = 0f..100f,
            steps = 0,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                activeTickColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.tertiary,
                inactiveTickColor = MaterialTheme.colorScheme.tertiary,
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UserLimitSliderPreview() {
    AppTheme(isDark = false) {
        Spacer(Modifier.height(100.dp))
        UserLimitSlider(
            sliderValue = 0f,
            onSliderValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserLimitSliderPreview1() {
    AppTheme(isDark = false) {
        Column() {
            Spacer(Modifier.height(100.dp))
            UserLimitSlider(
                sliderValue = 25f,
                onSliderValueChange = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserLimitSliderPreview2() {
    AppTheme(isDark = true) {
        Spacer(Modifier.height(100.dp))
        UserLimitSlider(
            sliderValue = 0f,
            onSliderValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserLimitSliderPreview3() {
    AppTheme(isDark = true) {
        Spacer(Modifier.height(100.dp))
        UserLimitSlider(
            sliderValue = 25f,
            onSliderValueChange = {}
        )
    }
}
