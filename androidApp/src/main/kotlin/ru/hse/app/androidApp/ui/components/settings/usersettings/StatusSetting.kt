package ru.hse.app.androidApp.ui.components.settings.usersettings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.state.StatusCircle
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun StatusSetting(
    selectedOption: StatusPresentation,
    modifier: Modifier = Modifier,
    onArrowClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        VariableLight(
            text = "Статус",
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier
                .clickable { onArrowClick() },
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(13.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer),
            ) {
                Row(
                    modifier = Modifier.padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    StatusCircle(
                        selectedOption,
                        size = 20.dp
                    )
                    VariableLight(
                        text = selectedOption.label,
                        fontSize = 16.sp
                    )
                }
            }

            Icon(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "Accept",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(14.dp)
                    .rotate(90.0F)
            )
        }
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingLightOffPreview() {
    val selectedOption = remember { mutableStateOf(StatusPresentation.ACTIVE) }
    AppTheme(isDark = false) {

        StatusSetting(
            selectedOption = selectedOption.value,
            onArrowClick = {},
        )
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingLightOffPreview1() {
    val selectedOption = remember { mutableStateOf(StatusPresentation.NOT_ACTIVE) }
    AppTheme(isDark = false) {

        StatusSetting(
            selectedOption = selectedOption.value,
            onArrowClick = {},
        )
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingLightOffPreview2() {
    val selectedOption = remember { mutableStateOf(StatusPresentation.DO_NOT_DISTURB) }
    AppTheme(isDark = false) {

        StatusSetting(
            selectedOption = selectedOption.value,
            onArrowClick = {},
        )
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingLightOffPreview3() {
    val selectedOption = remember { mutableStateOf(StatusPresentation.INVISIBLE) }
    AppTheme(isDark = false) {

        StatusSetting(
            selectedOption = selectedOption.value,
            onArrowClick = {},
        )
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingDarkOffPreview() {
    val selectedOption = remember { mutableStateOf(StatusPresentation.ACTIVE) }
    AppTheme(isDark = true) {

        StatusSetting(
            selectedOption = selectedOption.value,
            onArrowClick = {},
        )
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingDarkOffPreview1() {
    val selectedOption = remember { mutableStateOf(StatusPresentation.NOT_ACTIVE) }
    AppTheme(isDark = true) {

        StatusSetting(
            selectedOption = selectedOption.value,
            onArrowClick = {},
        )
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingDarkOffPreview2() {
    val selectedOption = remember { mutableStateOf(StatusPresentation.DO_NOT_DISTURB) }
    AppTheme(isDark = true) {

        StatusSetting(
            selectedOption = selectedOption.value,
            onArrowClick = {},
        )
    }
}

@Preview(
    name = "Light Theme - Switch Off",
    showBackground = true,
    widthDp = 360
)
@Composable
fun ThemeChangeSettingDarkOffPreview3() {
    val selectedOption = remember { mutableStateOf(StatusPresentation.INVISIBLE) }
    AppTheme(isDark = true) {

        StatusSetting(
            selectedOption = selectedOption.value,
            onArrowClick = {},
        )
    }
}