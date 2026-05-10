package ru.hse.app.hasslspace.screen.chats

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
import ru.hse.app.hasslspace.ui.components.chats.newmessage.NewMessageScreenContent
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.entity.model.chats.ChatsUiState
import ru.hse.app.hasslspace.ui.entity.model.chats.events.StartChatEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.events.LoadUserFriendsEvent
import ru.hse.app.hasslspace.ui.navigation.NavigationItem

@Composable
fun NewMessageScreen(
    navController: NavController,
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val loadUserFriendsEvent by viewModel.loadUserFriendsEvent.collectAsState()
    val startChatEvent by viewModel.startChatEvent.collectAsState()

    LaunchedEffect(startChatEvent) {
        when (startChatEvent) {
            is StartChatEvent.Success -> {
                val chatId = (startChatEvent as StartChatEvent.Success).chatId

                navController.navigate(NavigationItem.Chat.route + "/${chatId}")

            }

            is StartChatEvent.Error -> {
                val message = (startChatEvent as StartChatEvent.Error).message
                val exception = (startChatEvent as StartChatEvent.Error).exception
                viewModel.errorHandler.handleError(message, exception)
            }

            null -> {}
        }
        viewModel.resetStartChatEvent()
    }

    LaunchedEffect(loadUserFriendsEvent) {
        when (loadUserFriendsEvent) {
            is LoadUserFriendsEvent.SuccessLoad -> {}

            is LoadUserFriendsEvent.Error -> {
                val message =
                    (loadUserFriendsEvent as LoadUserFriendsEvent.Error).message
                val exception =
                    (loadUserFriendsEvent as LoadUserFriendsEvent.Error).exception
                viewModel.errorHandler.handleError(message, exception)
            }

            null -> {}
        }
        viewModel.resetLoadUserFriendsEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ChatsUiState.Loading -> {
                LoadingScreen()
            }

            is ChatsUiState.Error -> {
                ErrorScreen()
            }

            is ChatsUiState.Success -> {
                NewMessageWithStateContent(
                    uiState = uiState as ChatsUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun NewMessageWithStateContent(
    uiState: ChatsUiState.Success,
    navController: NavController,
    viewModel: ChatsViewModel
) {
    val context = LocalContext.current
    NewMessageScreenContent(
        imageLoader = viewModel.imageLoader,
        friends = uiState.data.friends,
        onBackClick = { navController.popBackStack() },
        onFriendClick = { viewModel.onMessageClick(it.id) },
        onAddFriendClick = {},
        onNewGroupClick = {},
        searchText = viewModel.searchTextFriends.value,
        onValueChange = viewModel::onSearchFriendsValueChange,
        isDarkTheme = viewModel.isDark,
    )
}