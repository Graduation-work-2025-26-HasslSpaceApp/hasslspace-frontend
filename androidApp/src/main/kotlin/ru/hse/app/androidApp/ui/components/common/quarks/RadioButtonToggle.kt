package ru.hse.app.androidApp.ui.components.common.quarks

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.R

@Composable
fun RadioButtonToggle(
    isChosen: Boolean,
    onToggle: (Boolean) -> Unit
) {
    IconButton(
        onClick = { onToggle(!isChosen) },
        modifier = Modifier.size(30.dp, 30.dp)
    ) {
        Icon(
            painter = if (isChosen) painterResource(id = R.drawable.radio_button_enabled) else painterResource(
                id = R.drawable.radio_button_disabled
            ),
            contentDescription = null,
            modifier = Modifier.size(25.dp, 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
fun RadioButtonTogglePreview() {
    val isChosen = remember { mutableStateOf(false) }
    RadioButtonToggle(
        isChosen = isChosen.value,
        onToggle = { isChosen.value = it }
    )
}
