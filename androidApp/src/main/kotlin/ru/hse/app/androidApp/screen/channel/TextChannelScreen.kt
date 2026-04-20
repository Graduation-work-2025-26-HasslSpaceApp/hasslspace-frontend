package ru.hse.app.androidApp.screen.channel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import ru.hse.app.androidApp.ui.components.chats.chat.ChatContent
import ru.hse.app.androidApp.ui.components.common.card.participantsLabel
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.chats.ChatUiState
import ru.hse.app.androidApp.ui.entity.model.chats.events.GetPrivateChatMessagesEvent
import ru.hse.app.androidApp.ui.entity.model.chats.events.SendMessageEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.JoinServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.LoadTextChannelEvent
import ru.hse.app.androidApp.ui.navigation.BottomNavigationItem

@Composable
fun TextChannelScreen(
    serverId: String,
    channelId: String,
    chatId: String,
    curUserId: String,
    navController: NavController,
    viewModel: TextChannelViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(serverId, channelId, chatId, curUserId) {
        viewModel.loadTextChannelInitInfo(curUserId, serverId, chatId, channelId)
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.refreshMessages(chatId, channelId)
        }
    }

    val getPrivateChatMessagesEvent by viewModel.getPrivateChatMessagesEvent.collectAsState()
    val loadTextChannelEvent by viewModel.loadTextChannelEvent.collectAsState()
    val sendMessageEvent by viewModel.sendMessageEvent.collectAsState()
    val joinServerEvent by viewModel.joinServerEvent.collectAsState()

    LaunchedEffect(joinServerEvent) {
        when (joinServerEvent) {
            is JoinServerEvent.Success -> {
                viewModel.handleError("Успешно присоединились к серверу")
                navController.navigate(BottomNavigationItem.Servers.route)
            }

            is JoinServerEvent.Error -> {
                val message = (joinServerEvent as JoinServerEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetJoinServerEvent()
    }

    LaunchedEffect(sendMessageEvent) {
        when (sendMessageEvent) {
            is SendMessageEvent.Success -> {}

            is SendMessageEvent.Error -> {
                val message =
                    (sendMessageEvent as SendMessageEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetSendMessageEvent()
    }

    LaunchedEffect(getPrivateChatMessagesEvent) {
        when (getPrivateChatMessagesEvent) {
            is GetPrivateChatMessagesEvent.SuccessLoad -> {}

            is GetPrivateChatMessagesEvent.Error -> {
                val message =
                    (getPrivateChatMessagesEvent as GetPrivateChatMessagesEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetGetPrivateChatMessagesEvent()
    }

    LaunchedEffect(loadTextChannelEvent) {
        when (loadTextChannelEvent) {
            is LoadTextChannelEvent.SuccessLoad -> {}

            is LoadTextChannelEvent.Error -> {
                val message =
                    (loadTextChannelEvent as LoadTextChannelEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetLoadTextChannelEvent()
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
                TextChannelWithStateContent(
                    uiState = uiState as ChatUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun TextChannelWithStateContent(
    uiState: ChatUiState.Success,
    navController: NavController,
    viewModel: TextChannelViewModel
) {
    val members = uiState.data.channelMembers
    ChatContent(
        channelName = uiState.data.name,
        channelSubtitle = participantsLabel(members.size),
        onBackClick = { navController.popBackStack() },
        onAuthorClick = {/*todo*/ },
        onSendMessage = { viewModel.addCurrentUserMessage(uiState.data.id, it) },
        isDarkTheme = viewModel.isDark,
        imageLoader = viewModel.imageLoader,
        messages = uiState.data.messages,
        onReadMsg = { viewModel.markMessageAsRead(it) },
        onCodeExtracted = { code -> viewModel.joinServer(code) }
    )
}