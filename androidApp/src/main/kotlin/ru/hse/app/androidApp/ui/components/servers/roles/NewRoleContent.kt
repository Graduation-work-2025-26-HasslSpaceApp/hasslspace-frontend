package ru.hse.app.androidApp.ui.components.servers.roles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.imageLoader
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.UserCardCheckbox
import ru.hse.app.androidApp.ui.components.common.field.AuthCustomField
import ru.hse.app.androidApp.ui.components.common.grid.UniversalVerticalGrid
import ru.hse.app.androidApp.ui.components.common.text.VariableBold
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.StatusPresentation
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun NewRoleContent(
    imageLoader: ImageLoader,
    friends: List<FriendCheckboxUiModel>,
    onBackClick: () -> Unit,
    roleName: String,
    onRoleNameChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: MutableState<Color>,
    onColorPickClick: () -> Unit,
    isDarkTheme: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BackButton(onClick = onBackClick)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                VariableBold(
                    text = "Новая роль",
                    fontSize = 20.sp,
                )
            }

            AddTextButton(
                text = "Сохранить",
                onClick = onSaveClick
            )
        }
        Spacer(Modifier.height(15.dp))

        AuthCustomField(
            text = roleName,
            onTextChanged = onRoleNameChanged,
            placeholder = "Придумайте название",
            description = "Название роли",
            maxCharacters = 30,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(15.dp))

        ColorSetting(
            selectedColor = selectedColor,
            onColorPickClick = onColorPickClick
        )

        Spacer(Modifier.height(15.dp))

        VariableLight(
            text = "Выберите участников",
            fontSize = 16.sp,
        )

        Spacer(Modifier.height(15.dp))

        UniversalVerticalGrid(
            items = friends,
            columns = 1,
            contentPadding = PaddingValues(0.dp),
        ) { friend ->
            UserCardCheckbox(
                imageLoader = imageLoader,
                username = friend.name,
                nickname = friend.nickname,
                status = friend.status,
                profilePictureUrl = friend.avatarUrl,
                isDarkTheme = isDarkTheme,
                onCardClick = {},
                onToggle = onToggle,
                isChosen = friend.isChosen
            )
        }
    }
}

private val mockFriends = listOf(
    FriendCheckboxUiModel(
        id = "1",
        name = "Марина Ландышева",
        nickname = "marina_flower",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        isChosen = true
    ),
    FriendCheckboxUiModel(
        id = "2",
        name = "Александр Иванов",
        nickname = "alex_ivanov",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        isChosen = false
    ),
    FriendCheckboxUiModel(
        id = "3",
        name = "Сергей Петров",
        nickname = "sergey_p",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        isChosen = true
    ),
    FriendCheckboxUiModel(
        id = "4",
        name = "Екатерина Смирнова",
        nickname = "katya_smirnova",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        isChosen = false
    ),
    FriendCheckboxUiModel(
        id = "5",
        name = "Дмитрий Козлов",
        nickname = "dmitry_k",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        isChosen = false
    ),
    FriendCheckboxUiModel(
        id = "6",
        name = "Анна Морозова",
        nickname = "anna_moroz",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        isChosen = true
    ),
    FriendCheckboxUiModel(
        id = "7",
        name = "Иван Сидоров",
        nickname = "ivan_sidorov",
        status = StatusPresentation.ACTIVE,
        avatarUrl = "",
        isChosen = false
    )
)

@Preview(showBackground = true)
@Composable
fun NewRoleContentPreviewWithRequestsLight() {
    AppTheme(isDark = false) {
        NewRoleContent(
            imageLoader = LocalContext.current.imageLoader,
            friends = mockFriends,
            onBackClick = {},
            roleName = "",
            onRoleNameChanged = {},
            onSaveClick = {},
            onToggle = {},
            isDarkTheme = false,
            selectedColor = remember { mutableStateOf(Color.Blue) },
            onColorPickClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewRoleContentPreviewWithRequestsDark() {
    AppTheme(isDark = true) {
        NewRoleContent(
            imageLoader = LocalContext.current.imageLoader,
            friends = mockFriends,
            onBackClick = {},
            roleName = "",
            onRoleNameChanged = {},
            onSaveClick = {},
            onToggle = {},
            isDarkTheme = true,
            selectedColor = remember { mutableStateOf(Color.Blue) },
            onColorPickClick = {}
        )
    }
}