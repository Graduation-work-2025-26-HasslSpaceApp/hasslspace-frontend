package ru.hse.app.hasslspace.ui.components.profile.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.hse.app.hasslspace.ui.components.common.card.ProfileCard
import ru.hse.app.hasslspace.ui.theme.AppTheme

@Composable
fun ServersCard(
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ProfileCard(
        type = "Серверы",
        label = serversLabel(count),
        modifier = modifier,
        onClick = onClick,
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
            ServersCard(1, onClick = {})
            ServersCard(2, onClick = {})
            ServersCard(5, onClick = {})
            ServersCard(11, onClick = {})
            ServersCard(21, onClick = {})
            ServersCard(24, onClick = {})
            ServersCard(100, onClick = {})
        }
    }
}

@Preview(showBackground = true, name = "All cases")
@Composable
fun ServersCardPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ServersCard(1, onClick = {})
            ServersCard(2, onClick = {})
            ServersCard(5, onClick = {})
            ServersCard(11, onClick = {})
            ServersCard(21, onClick = {})
            ServersCard(24, onClick = {})
            ServersCard(100, onClick = {})
        }
    }
}

