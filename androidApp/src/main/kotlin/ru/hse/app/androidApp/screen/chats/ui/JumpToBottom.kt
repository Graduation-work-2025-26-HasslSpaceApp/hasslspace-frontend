package ru.hse.app.androidApp.screen.chats.ui

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.theme.AppTheme

private enum class Visibility {
    VISIBLE,
    GONE,
}

/**
 * Shows a button that lets the user scroll to the bottom.
 */
@Composable
fun JumpToBottom(enabled: Boolean, onClicked: () -> Unit, modifier: Modifier = Modifier) {
    val transition = updateTransition(
        if (enabled) Visibility.VISIBLE else Visibility.GONE,
        label = "JumpToBottom visibility animation",
    )
    val bottomOffset by transition.animateDp(label = "JumpToBottom offset animation") {
        if (it == Visibility.GONE) {
            (-32).dp
        } else {
            32.dp
        }
    }
    if (bottomOffset > 0.dp) {
        ExtendedFloatingActionButton(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    modifier = Modifier.height(18.dp).rotate(90F),
                    contentDescription = null,
                )
            },
            text = {
                Text("Перейти в конец сообщений")
            },
            onClick = onClicked,
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .offset(x = 0.dp, y = -bottomOffset)
                .height(36.dp),
        )
    }
}

@Preview
@Composable
fun JumpToBottomPreview() {
    AppTheme(
        isDark = true
    ) {
        JumpToBottom(
            modifier = Modifier.padding(top = 50.dp),
            enabled = true, onClicked = {})
    }
}

@Preview
@Composable
fun JumpToBottomPreviewLight() {
    AppTheme(
        isDark = false
    ) {
        JumpToBottom(
            modifier = Modifier.padding(top = 50.dp),
            enabled = true, onClicked = {})
    }
}
