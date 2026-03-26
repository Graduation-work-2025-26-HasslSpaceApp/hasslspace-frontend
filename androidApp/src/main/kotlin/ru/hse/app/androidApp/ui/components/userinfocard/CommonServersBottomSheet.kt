package ru.hse.app.androidApp.ui.components.userinfocard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.card.ServerCard
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.entity.model.ServerShortUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonServersBottomSheet(
    imageLoader: ImageLoader,
    servers: List<ServerShortUiModel>,
    onServerClick: (ServerShortUiModel) -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.background,
        scrimColor = MaterialTheme.colorScheme.surfaceDim.copy(alpha = 0.5f),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = MaterialTheme.colorScheme.outline
            )
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {
            VariableBold(
                text = "Общие серверы",
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
            )

            UniversalVerticalGrid(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = servers,
                columns = 1
            ) { server ->
                ServerCard(
                    imageLoader = imageLoader,
                    name = server.name,
//                    participantCount = server.participantCount,
                    serverPictureUrl = server.avatarUrl,
                    isDarkTheme = isDarkTheme,
                    onCardClick = { onServerClick(server) },
                )
            }
        }
    }
}

private fun previewServer(id: String, name: String, count: Int) = ServerShortUiModel(
    id = id,
    name = name,
    avatarUrl = "",
//    participantCount = count,
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
@OptIn(ExperimentalMaterial3Api::class)
fun CommonServersBottomSheetLight() {

    AppTheme(isDark = false) {
        CommonServersBottomSheet(
            imageLoader = ImageLoader(LocalContext.current),
            servers = servers,
            onServerClick = {},
            onDismiss = {},
            isDarkTheme = false
        )
    }
}

@Preview(showBackground = true, name = "Friends Screen - With Requests (Dark)")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CommonServersBottomSheetDark() {

    AppTheme(isDark = true) {
        CommonServersBottomSheet(
            imageLoader = ImageLoader(LocalContext.current),
            servers = servers,
            onServerClick = {},
            onDismiss = {},
            isDarkTheme = true
        )
    }
}