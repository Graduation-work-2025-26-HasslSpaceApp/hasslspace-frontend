package ru.hse.app.hasslspace.ui.components.common.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    progressIndicatorStrokeWidth: Dp = 4.dp,
    progressIndicatorTrackColor: Color = MaterialTheme.colorScheme.outline,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = progressIndicatorColor,
            strokeWidth = progressIndicatorStrokeWidth,
            trackColor = progressIndicatorTrackColor,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenWrapperPreview_Loading() {
    val isLoading = remember { mutableStateOf(true) }

    AppTheme(isDark = false) {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenWrapperPreview_Loading_Dark() {
    val isLoading = remember { mutableStateOf(true) }

    AppTheme(isDark = true) {
        LoadingScreen()
    }
}