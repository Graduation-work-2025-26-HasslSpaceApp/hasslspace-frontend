package ru.hse.app.androidApp.ui.components.common.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.quarks.CheckboxToggle
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun RoleCardCheckbox(
    title: String,
    modifier: Modifier = Modifier,
    height: Dp = 77.dp,
    color: Color,
    onClick: () -> Unit,
    isChosen: Boolean = false,
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(22.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {

        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(color, shape = CircleShape),
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            VariableMedium(
                text = title,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 5.dp),
        ) {

            CheckboxToggle(
                isChosen = isChosen,
                onClick = onClick,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses - Light")
@Composable
fun RoleCardCheckboxPreviewAllLight() {
    val isChosen = remember { mutableStateOf(false) }
    AppTheme(isDark = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            RoleCardCheckbox(
                title = "role 1",
                color = Color.Blue,
                isChosen = isChosen.value,
                onClick = {isChosen.value = !isChosen.value},
            )

            Spacer(modifier = Modifier.height(12.dp))

            RoleCardCheckbox(
                title = "role 2",
                color = Color.Red,
                isChosen = isChosen.value,
                onClick = {},
            )

            Spacer(modifier = Modifier.height(12.dp))

            RoleCardCheckbox(
                title = "role 3",
                color = Color.Green,
                isChosen = isChosen.value,
                onClick = {},
            )
        }
    }
}

@Preview(showBackground = true, name = "All statuses - Dark")
@Composable
fun RoleCardCheckboxPreviewAllDark() {
    val isChosen = remember { mutableStateOf(false) }
    AppTheme(isDark = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            RoleCardCheckbox(
                title = "role 1",
                color = Color.Blue,
                isChosen = isChosen.value,
                onClick = {},
            )

            Spacer(modifier = Modifier.height(12.dp))

            RoleCardCheckbox(
                title = "role 2",
                color = Color.Red,
                isChosen = isChosen.value,
                onClick = {},
            )

            Spacer(modifier = Modifier.height(12.dp))

            RoleCardCheckbox(
                title = "role 3",
                color = Color.Green,
                isChosen = isChosen.value,
                onClick = {},
            )
        }
    }
}
