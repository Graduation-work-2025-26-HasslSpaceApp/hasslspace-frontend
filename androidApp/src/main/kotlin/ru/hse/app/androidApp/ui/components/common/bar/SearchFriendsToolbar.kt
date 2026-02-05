package ru.hse.app.androidApp.ui.components.common.bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.ui.components.common.button.AddTextButton
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun SearchFriendsToolbar(
    modifier: Modifier = Modifier,
    searchValue: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    infoText: String? = null,
    error: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        VariableLight(
            text = "Вы можете добавить друзей в HasslSpace по никнейму",
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        SearchBar(
            text = searchValue,
            placeholder = "Введите никнейм",
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            AddTextButton(
                onClick = onSendClick,
                text = "Отправить заявку"
            )
        }
        if (infoText != null) {
            Spacer(modifier = Modifier.height(12.dp))
            VariableLight(
                text = infoText,
                fontSize = 12.sp,
                fontColor = if (error) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun SearchFriendsToolbarPreviewLight() {
    val text = remember { mutableStateOf("") }
    AppTheme(
        isDark = false
    ) {
        SearchFriendsToolbar(
            searchValue = text.value,
            onValueChange = { text.value = it },
            onSendClick = {}
        )
    }
}

@Preview
@Composable
fun SearchFriendsToolbarPreviewLightNotEmpty() {
    val text = remember { mutableStateOf("sdfjs") }
    AppTheme(
        isDark = false
    ) {
        SearchFriendsToolbar(
            searchValue = text.value,
            onValueChange = { text.value = it },
            onSendClick = {}
        )
    }
}

@Preview
@Composable
fun SearchFriendsToolbarPreviewLightError() {
    val text = remember { mutableStateOf("sdfjs") }
    AppTheme(
        isDark = false
    ) {
        SearchFriendsToolbar(
            searchValue = text.value,
            onValueChange = { text.value = it },
            onSendClick = {},
            infoText = "Хм... Не получилось. Проверьте, что вы ввели правильное имя пользователя",
            error = true
        )
    }
}

@Preview
@Composable
fun SearchFriendsToolbarPreviewLightSuccess() {
    val text = remember { mutableStateOf("sdfjs") }
    AppTheme(
        isDark = false
    ) {
        SearchFriendsToolbar(
            searchValue = text.value,
            onValueChange = { text.value = it },
            onSendClick = {},
            infoText = "Получилось! Отправили заявку в друзья пользователю @yuulkht"
        )
    }
}

@Preview
@Composable
fun SearchFriendsToolbarPreviewDark() {
    val text = remember { mutableStateOf("") }
    AppTheme(
        isDark = true
    ) {
        SearchFriendsToolbar(
            searchValue = text.value,
            onValueChange = { text.value = it },
            onSendClick = {}
        )
    }
}

@Preview
@Composable
fun SearchFriendsToolbarPreviewDarkNotEmpty() {
    val text = remember { mutableStateOf("sdfsf") }
    AppTheme(
        isDark = true
    ) {
        SearchFriendsToolbar(
            searchValue = text.value,
            onValueChange = { text.value = it },
            onSendClick = {}
        )
    }
}

@Preview
@Composable
fun SearchFriendsToolbarPreviewDarkError() {
    val text = remember { mutableStateOf("sdfjs") }
    AppTheme(
        isDark = true
    ) {
        SearchFriendsToolbar(
            searchValue = text.value,
            onValueChange = { text.value = it },
            onSendClick = {},
            infoText = "Хм... Не получилось. Проверьте, что вы ввели правильное имя пользователя",
            error = true
        )
    }
}

@Preview
@Composable
fun SearchFriendsToolbarPreviewDarkSuccess() {
    val text = remember { mutableStateOf("sdfjs") }
    AppTheme(
        isDark = true
    ) {
        SearchFriendsToolbar(
            searchValue = text.value,
            onValueChange = { text.value = it },
            onSendClick = {},
            infoText = "Получилось! Отправили заявку в друзья пользователю @yuulkht"
        )
    }
}