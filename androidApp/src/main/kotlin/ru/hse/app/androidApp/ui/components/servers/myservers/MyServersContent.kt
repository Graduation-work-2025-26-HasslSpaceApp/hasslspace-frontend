package ru.hse.app.androidApp.ui.components.servers.myservers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.bar.SearchBar
import ru.hse.app.androidApp.ui.components.common.box.NoItemsBox
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.card.ServerCard
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.ServerShortUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun MyServersContent(
    imageLoader: ImageLoader,
    servers: List<ServerShortUiModel>,
    onAddClick: () -> Unit,
    onServerClick: (ServerShortUiModel) -> Unit,
    searchText: MutableState<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            VariableBold(
                text = "Мои серверы",
                fontSize = 28.sp,
                modifier = Modifier.weight(1f)
            )
            AddTextButton(
                onClick = onAddClick
            )
        }
        Spacer(Modifier.height(15.dp))
        SearchBar(
            text = searchText,
            onValueChange = onValueChange
        )
        Spacer(Modifier.height(15.dp))

        if (servers.isEmpty()) {
            NoItemsBox("Вы пока не являетесь участником серверов")
        }

        UniversalVerticalGrid(
            items = servers,
            columns = 1
        ) { server ->
            ServerCard(
                imageLoader = imageLoader,
                name = server.name,
                participantCount = server.participantCount,
                serverPictureUrl = server.avatarUrl,
                isDarkTheme = isDarkTheme,
                onCardClick = { onServerClick(server) },
            )
        }
    }
}

private fun previewServer(id: String, name: String, count: Int) = ServerShortUiModel(
    id = id,
    name = name,
    avatarUrl = "",
    participantCount = count,
)

val servers = listOf(
    previewServer("1", "Александр Иванов", 2),
    previewServer("2", "Мария Петрова", 5),
    previewServer("3", "Дмитрий Сидоров", 21),
    previewServer("4", "Александр Иванов", 22),
    previewServer("5", "Мария Петрова", 55),
    previewServer("6", "Дмитрий Сидоров", 101),
    previewServer("7", "Александр Иванов", 89),
    previewServer("8", "Мария Петрова", 22),
    previewServer("9", "Дмитрий Сидоров", 22),
    previewServer("10", "Александр Иванов", 22),
    previewServer("11", "Мария Петрова", 22),
    previewServer("12", "Дмитрий Сидоров", 22),
)

@Preview(showBackground = true, name = "Friends Screen - With Requests (Light)")
@Composable
fun FriendsContentPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        MyServersContent(
            imageLoader = ImageLoader(LocalContext.current),
            servers = servers,
            onServerClick = {},
            onAddClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - With Requests (Dark)")
@Composable
fun FriendsContentPreviewWithRequestsDark() {

    AppTheme(isDark = true) {
        MyServersContent(
            imageLoader = ImageLoader(LocalContext.current),
            servers = servers,
            onServerClick = {},
            onAddClick = {},
            searchText = remember { mutableStateOf("") },
            onValueChange = {},
            isDarkTheme = true
        )
    }
}


