package ru.hse.app.androidApp.ui.components.servers.servercard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.button.BackButton
import ru.hse.app.androidApp.ui.components.common.card.TextChannelCard
import ru.hse.app.androidApp.ui.components.common.card.VoiceChannelCard
import ru.hse.app.androidApp.ui.components.common.text.VariableLight
import ru.hse.app.androidApp.ui.components.common.text.VariableMedium
import ru.hse.app.androidApp.ui.entity.model.TextChannelUiModel
import ru.hse.app.androidApp.ui.entity.model.VoiceChannelUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@Composable
fun ServerCardContent(
    onBackClick: () -> Unit,
    onServerNameClick: () -> Unit,
    onAddPeopleClick: () -> Unit,
    serverName: String,
    textChannels: List<TextChannelUiModel>,
    voiceChannels: List<VoiceChannelUiModel>,
    onTextChannelShortClick: (TextChannelUiModel) -> Unit,
    onTextChannelLongClick: (TextChannelUiModel) -> Unit,
    onVoiceChannelShortClick: (VoiceChannelUiModel) -> Unit,
    onVoiceChannelLongClick: (VoiceChannelUiModel) -> Unit,
    onTextChannelsClick: () -> Unit,
    onVoiceChannelsClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(onClick = onBackClick)
            Spacer(Modifier.width(10.dp))

            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onServerNameClick),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VariableMedium(
                    text = serverName,
                    fontSize = 20.sp
                )
                Icon(
                    painter = painterResource(R.drawable.arrow),
                    contentDescription = "goto",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .size(10.dp)
                )
            }

            Icon(
                painter = painterResource(if (isDarkTheme) R.drawable.add_people_dark else R.drawable.add_people_light),
                contentDescription = "add",
                tint = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable(onClick = onAddPeopleClick)
            )
        }
        Spacer(Modifier.height(15.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier,
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                VariableLight(
                    text = "Текстовые каналы",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {},
                            onLongClick = onTextChannelsClick
                        )
                )
                Spacer(Modifier.height(5.dp))
            }

            items(textChannels, key = { it.id }) { channel ->
                TextChannelCard(
                    name = channel.title,
                    onShortClick = { onTextChannelShortClick(channel) },
                    onLongClick = { onTextChannelLongClick(channel) }
                )
            }

            item {
                VariableLight(
                    text = "Голосовые каналы",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {},
                            onLongClick = onVoiceChannelsClick
                        )
                )
                Spacer(Modifier.height(5.dp))
            }

            items(voiceChannels, key = { it.id }) { channel ->
                VoiceChannelCard(
                    name = channel.title,
                    onShortClick = { onVoiceChannelShortClick(channel) },
                    onLongClick = { onVoiceChannelLongClick(channel) }
                )
            }
        }
    }

}

val textChannels = listOf(
    TextChannelUiModel(id = "1", title = "основной"),
    TextChannelUiModel(id = "2", title = "основной 2"),
    TextChannelUiModel(id = "3", title = "основной 3"),
    TextChannelUiModel(id = "4", title = "основной 4"),
    TextChannelUiModel(id = "5", title = "основной 5"),
    TextChannelUiModel(id = "6", title = "general"),
    TextChannelUiModel(id = "7", title = "чат"),
//    TextChannelUiModel(id = "8", title = "команда"),
//    TextChannelUiModel(id = "9", title = "проекты"),
//    TextChannelUiModel(id = "10", title = "помощь"),
//    TextChannelUiModel(id = "11", title = "идеи"),
//    TextChannelUiModel(id = "12", title = "оффтоп"),
//    TextChannelUiModel(id = "13", title = "музыка"),
//    TextChannelUiModel(id = "14", title = "игры"),
//    TextChannelUiModel(id = "15", title = "новости"),
//    TextChannelUiModel(id = "16", title = "анонсы"),
//    TextChannelUiModel(id = "17", title = "вопросы"),
//    TextChannelUiModel(id = "18", title = "поддержка"),
//    TextChannelUiModel(id = "19", title = "фидбек"),
//    TextChannelUiModel(id = "20", title = "разработка"),
//    TextChannelUiModel(id = "21", title = "дизайн"),
//    TextChannelUiModel(id = "22", title = "тестирование"),
//    TextChannelUiModel(id = "23", title = "документация"),
//    TextChannelUiModel(id = "24", title = "встречи"),
//    TextChannelUiModel(id = "25", title = "планирование")
)

val voiceChannels = listOf(
    VoiceChannelUiModel(id = "26", title = "Основной"),
    VoiceChannelUiModel(id = "27", title = "Основной 2"),
    VoiceChannelUiModel(id = "28", title = "Основной 3"),
    VoiceChannelUiModel(id = "29", title = "Основной 4"),
    VoiceChannelUiModel(id = "30", title = "Основной 5"),
    VoiceChannelUiModel(id = "31", title = "Общий голосовой"),
    VoiceChannelUiModel(id = "32", title = "Музыка"),
    VoiceChannelUiModel(id = "33", title = "Игры"),
    VoiceChannelUiModel(id = "34", title = "Работа"),
    VoiceChannelUiModel(id = "35", title = "Встречи"),
    VoiceChannelUiModel(id = "36", title = "Перерыв"),
    VoiceChannelUiModel(id = "37", title = "AFK"),
    VoiceChannelUiModel(id = "38", title = "Поддержка"),
    VoiceChannelUiModel(id = "39", title = "Совещания"),
    VoiceChannelUiModel(id = "40", title = "Стрим"),
    VoiceChannelUiModel(id = "41", title = "Тихое общение"),
    VoiceChannelUiModel(id = "42", title = "Срочные вопросы"),
    VoiceChannelUiModel(id = "43", title = "Презентации"),
    VoiceChannelUiModel(id = "44", title = "Коуч-сессии"),
    VoiceChannelUiModel(id = "45", title = "Мозговой штурм")
)

@Preview(showBackground = true)
@Composable
fun ServerCardContentPreviewWithRequestsLight() {

    AppTheme(isDark = false) {
        ServerCardContent(
            serverName = "тест сервер",
            onBackClick = {},
            onServerNameClick = {},
            onAddPeopleClick = {},
            textChannels = textChannels,
            voiceChannels = voiceChannels,
            onTextChannelShortClick = {},
            onTextChannelLongClick = {},
            onVoiceChannelShortClick = {},
            onVoiceChannelLongClick = {},
            isDarkTheme = false,
            onTextChannelsClick = {},
            onVoiceChannelsClick = {}
        )
    }
}