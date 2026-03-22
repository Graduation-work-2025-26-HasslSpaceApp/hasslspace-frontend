package ru.hse.app.androidApp.screen.chats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.imageLoader
import ru.hse.app.androidApp.ui.components.chats.chat.ChatContent
import ru.hse.app.androidApp.ui.components.common.card.participantsLabel
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.chats.ChatUiState

@Composable
fun ChatScreen(
    chatId: String,
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(chatId) {
        viewModel.loadChatInitInfo(chatId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ChatUiState.Loading -> {
                LoadingScreen()
            }

            is ChatUiState.Error -> {
                ErrorScreen()
            }

            is ChatUiState.Success -> {
                ChatWithStateContent(
                    uiState = uiState as ChatUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun ChatWithStateContent(
    uiState: ChatUiState.Success,
    navController: NavController,
    viewModel: ChatViewModel
) {
    val members = uiState.data.channelMembers
    ChatContent(
        channelName = uiState.data.channelName,
        channelSubtitle = if (members.size > 2) participantsLabel(members.size) else "Личный чат",
        onBackClick = { navController.popBackStack() },
        onAuthorClick = {/*todo*/ },
        onSendMessage = viewModel::addCurrentUserMessage,
        me = uiState.data.currentUser,
        isDarkTheme = viewModel.isDark,
        imageLoader = LocalContext.current.imageLoader,
        messages = uiState.data.messages,
    )
}