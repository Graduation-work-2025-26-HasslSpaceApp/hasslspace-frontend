package ru.hse.app.androidApp.ui.components.servers.myservers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.card.ServerCard
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.entity.model.ServerShortUiModel

@Composable
fun MyServersList(
    contentPadding: PaddingValues = PaddingValues(16.dp),
    imageLoader: ImageLoader,
    isDarkTheme: Boolean,
    servers: List<ServerShortUiModel>,
    onServerClick: (ServerShortUiModel) -> Unit,
) {
    UniversalVerticalGrid(
        items = servers,
        columns = 1,
        contentPadding = contentPadding,
    ) { server ->
        ServerCard(
            imageLoader = imageLoader,
            name = server.name,
//            participantCount = server.participantCount,
            isDarkTheme = isDarkTheme,
            serverPictureUrl = server.avatarUrl,
            onCardClick = { onServerClick(server) }
        )
    }
}
