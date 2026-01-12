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
fun CheckboxToggle(
    isChosen: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = { onToggle(!isChosen) },
        modifier = modifier
    ) {
        Icon(
            painter = if (isChosen) painterResource(id = R.drawable.value_ok) else painterResource(
                id = R.drawable.empty_off
            ),
            contentDescription = null,
            modifier = Modifier.size(25.dp, 25.dp),
            tint = Color.Unspecified
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckboxTogglePreview() {
    val isChosen = remember { mutableStateOf(false) }
    CheckboxToggle(
        isChosen = isChosen.value,
        onToggle = { isChosen.value = it }
    )
}