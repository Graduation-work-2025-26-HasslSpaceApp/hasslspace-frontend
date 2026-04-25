package ru.hse.app.hasslspace.screen.chats

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
import ru.hse.app.hasslspace.ui.components.chats.chat.ChatContent
import ru.hse.app.hasslspace.ui.components.common.card.participantsLabel
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.entity.model.call.events.GetTokenEvent
import ru.hse.app.hasslspace.ui.entity.model.chats.ChatUiState
import ru.hse.app.hasslspace.ui.entity.model.chats.events.GetPrivateChatMessagesEvent
import ru.hse.app.hasslspace.ui.entity.model.chats.events.GetPrivateChatsEvent
import ru.hse.app.hasslspace.ui.entity.model.chats.events.SendMessageEvent
import ru.hse.app.hasslspace.ui.entity.model.servers.events.JoinServerEvent
import ru.hse.app.hasslspace.ui.navigation.BottomNavigationItem
import ru.hse.app.hasslspace.ui.navigation.NavigationItem

@Composable
fun ChatScreen(
    chatId: String,
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(chatId) {
        viewModel.loadChatInitInfo(chatId)
    }

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.refreshMessages(chatId)
        }
    }

    val getPrivateChatMessagesEvent by viewModel.getPrivateChatMessagesEvent.collectAsState()
    val getPrivateChatsEvent by viewModel.getPrivateChatsEvent.collectAsState()
    val sendMessageEvent by viewModel.sendMessageEvent.collectAsState()
    val joinServerEvent by viewModel.joinServerEvent.collectAsState()
    val getTokenEvent by viewModel.getTokenEvent.collectAsState()

    LaunchedEffect(getTokenEvent) {
        when (getTokenEvent) {
            is GetTokenEvent.Success -> {
                val token = (getTokenEvent as GetTokenEvent.Success).token
                val roomName = (getTokenEvent as GetTokenEvent.Success).roomName
                val videoEnabled = (getTokenEvent as GetTokenEvent.Success).videoEnabled
                val limit = (getTokenEvent as GetTokenEvent.Success).limit

                navController.navigate(NavigationItem.VoiceRoom.route + "/$token/$roomName/$videoEnabled/$limit")
            }

            is GetTokenEvent.Error -> {
                val message = (getTokenEvent as GetTokenEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetGetTokenEvent()
    }

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

    LaunchedEffect(getPrivateChatsEvent) {
        when (getPrivateChatsEvent) {
            is GetPrivateChatsEvent.SuccessLoad -> {}

            is GetPrivateChatsEvent.Error -> {
                val message =
                    (getPrivateChatsEvent as GetPrivateChatsEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetGetPrivateChatsEvent()
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
        channelName = uiState.data.name,
        channelSubtitle = if (members.size > 2) participantsLabel(members.size) else "Личный чат",
        onBackClick = { navController.popBackStack() },
        onAuthorClick = {/*todo*/ },
        onSendMessage = {text, attachments -> viewModel.addCurrentUserMessage(uiState.data.id, text, attachments) },
        isDarkTheme = viewModel.isDark,
        imageLoader = viewModel.imageLoader,
        messages = uiState.data.messages,
        onReadMsg = { viewModel.markMessageAsRead(it) },
        onCodeExtracted = { code -> viewModel.joinServer(code) },
        enabledCall = (members.size == 2),
        onCallClick = {
            val firstMember = uiState.data.channelMembers.firstOrNull { !it.isCurrentUser }
            val me = uiState.data.channelMembers.firstOrNull { it.isCurrentUser }
            if (firstMember != null && me != null) {
                viewModel.onCallClick(
                    uiState.data.id,
                    firstMember.id,
                    me.name,
                    "Звонок с ${firstMember.name}"
                )
            }

        }
    )
}