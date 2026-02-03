package ru.hse.app.androidApp.ui.components.common.box

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium

@Composable
fun NoItemsBox(
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        VariableMedium(
            text = text,
            fontSize = 16.sp,
            fontColor = MaterialTheme.colorScheme.onBackground
        )
    }
}