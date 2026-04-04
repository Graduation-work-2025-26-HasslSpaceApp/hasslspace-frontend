package ru.hse.app.androidApp.ui.components.common.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import ru.hse.app.androidApp.ui.components.common.card.UserCardCallMessage
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun <T> UniversalVerticalGrid(
    items: List<T>,
    columns: Int = 2,
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 12.dp,
    verticalSpacing: Dp = 12.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemContent: @Composable (item: T) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
        verticalArrangement = Arrangement.spacedBy(verticalSpacing)
    ) {
        items(items) { item ->
            itemContent(item)
        }
    }
}

data class TestModel(
    val username: String,
    val nickname: String,
    val profilePhotoUrl: String
)

@Preview(showBackground = true, name = "Users Grid - Light")
@Composable
fun UsersGridPreviewLight() {
    val users = listOf(
        TestModel("Александр Иванов", "alex_ivanov", ""),
        TestModel("Мария Петрова", "maria_petrova", ""),
        TestModel("Дмитрий Сидоров", "dmitry_sidorov", ""),
        TestModel("Екатерина Волкова", "ekaterina_volkova", ""),
        TestModel("Иван Николаев", "ivan_nikolaev", ""),
        TestModel("Анна Смирнова", "anna_smirnova", "")
    )

    AppTheme(isDark = false) {
        UniversalVerticalGrid(
            items = users,
            columns = 1
        ) { user ->
            UserCardCallMessage(
                username = user.username,
                nickname = user.nickname,
                profilePictureUrl = user.profilePhotoUrl,
                isDarkTheme = false,
                imageLoader = ImageLoader(LocalContext.current),
                onCardClick = {},
//                onCallClick = {},
//                onMessageClick = {}
            )
        }
    }
}

