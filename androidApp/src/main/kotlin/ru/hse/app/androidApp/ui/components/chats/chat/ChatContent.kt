package ru.hse.app.androidApp.ui.components.chats.chat

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.ImageLoader
import coil3.imageLoader
import kotlinx.coroutines.launch
import ru.hse.app.androidApp.ui.components.common.card.participantsLabel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.ChatMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.chats.MessageUiModel
import ru.hse.app.androidApp.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatContent(
    channelName: String,
    channelSubtitle: String,
    onBackClick: () -> Unit,
    onAuthorClick: (ChatMemberUiModel?) -> Unit,
    onSendMessage: (String) -> Unit,
    onReadMsg: (String) -> Unit,
    isDarkTheme: Boolean,
    imageLoader: ImageLoader,
    messages: List<MessageUiModel>,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ChannelNameBar(
                channelName = channelName,
                channelSubtitle = channelSubtitle,
                onNavIconPressed = onBackClick,
                scrollBehavior = scrollBehavior,
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
//            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues),
        ) {
            Messages(
                messages = messages.sortedByDescending { it.timestamp },
                scrollState = scrollState,
                onAuthorClick = onAuthorClick,
                isDarkTheme = isDarkTheme,
                imageLoader = imageLoader,
                modifier = Modifier.weight(1f),
                onReadMsg = onReadMsg
            )
            UserInput(
                onMessageSent = { content ->
                    onSendMessage(content)
                },
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
//                    .navigationBarsPadding()
                    .imePadding(),
                isDark = isDarkTheme,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatContentLight() {
    AppTheme(isDark = false) {
        ChatContent(
            channelName = "# composers",
            channelSubtitle = participantsLabel(count = 10),
            onBackClick = {},
            onAuthorClick = {},
            onSendMessage = {},
            isDarkTheme = false,
            imageLoader = LocalContext.current.imageLoader,
            messages = messages.sortedByDescending { it.timestamp },
            onReadMsg = {
                Log.d("ChatContentDark", "onReadMsg: $it")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatContentDark() {
    AppTheme(isDark = true) {
        ChatContent(
            channelName = "# composers",
            channelSubtitle = participantsLabel(count = 10),
            onBackClick = {},
            onAuthorClick = {},
            onSendMessage = {},
            isDarkTheme = true,
            imageLoader = LocalContext.current.imageLoader,
            messages = messages.sortedByDescending { it.timestamp },
            onReadMsg = {
                Log.d("ChatContentDark", "onReadMsg: $it")
            }
        )
    }
}

