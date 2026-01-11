package ru.hse.app.androidApp.ui.components.profile

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
fun FriendsCard(
    count: Int,
    modifier: Modifier = Modifier
) {
    ProfileCard(
        type = "Друзья",
        label = friendsLabel(count),
        modifier = modifier,
        cardColor = MaterialTheme.colorScheme.secondary,
        typeColor = MaterialTheme.colorScheme.onSecondary,
        labelColor = MaterialTheme.colorScheme.onSurface
    )
}

fun friendsLabel(count: Int): String {
    val mod10 = count % 10
    val mod100 = count % 100

    return when {
        mod100 in 11..14 -> "$count друзей"
        mod10 == 1 -> "$count друг"
        mod10 in 2..4 -> "$count друга"
        else -> "$count друзей"
    }
}

@Preview(showBackground = true, name = "All cases")
@Composable
fun FriendsCardPreviewAllLight() {
    AppTheme(isDark = false) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            FriendsCard(1)
            FriendsCard(2)
            FriendsCard(5)
            FriendsCard(11)
            FriendsCard(21)
            FriendsCard(24)
            FriendsCard(100)
        }
    }
}

@Preview(showBackground = true, name = "All cases")
@Composable
fun FriendsCardPreviewAllDark() {
    AppTheme(isDark = true) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            FriendsCard(1)
            FriendsCard(2)
            FriendsCard(5)
            FriendsCard(11)
            FriendsCard(21)
            FriendsCard(24)
            FriendsCard(100)
        }
    }
}





