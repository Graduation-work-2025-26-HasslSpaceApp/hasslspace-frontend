package ru.hse.app.androidApp.ui.components.profile.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.androidApp.ui.components.common.card.ProfileCard
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ServersCard(
    count: Int,
    modifier: Modifier = Modifier
) {
    ProfileCard(
        type = "Серверы",
        label = serversLabel(count),
        modifier = modifier,
        cardColor = MaterialTheme.colorScheme.tertiary,
        typeColor = MaterialTheme.colorScheme.onTertiary,
        labelColor = MaterialTheme.colorScheme.onSurface
    )
}

fun serversLabel(count: Int): String {
    val mod10 = count % 10
    val mod100 = count % 100

    return when {
        mod100 in 11..14 -> "$count серверов"
        mod10 == 1 -> "$count сервер"
        mod10 in 2..4 -> "$count сервера"
        else -> "$count серверов"
    }
}

@Preview(showBackground = true, name = "All cases")
@Composable
fun ServersCardPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ServersCard(1)
            ServersCard(2)
            ServersCard(5)
            ServersCard(11)
            ServersCard(21)
            ServersCard(24)
            ServersCard(100)
        }
    }
}

@Preview(showBackground = true, name = "All cases")
@Composable
fun ServersCardPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ServersCard(1)
            ServersCard(2)
            ServersCard(5)
            ServersCard(11)
            ServersCard(21)
            ServersCard(24)
            ServersCard(100)
        }
    }
}

