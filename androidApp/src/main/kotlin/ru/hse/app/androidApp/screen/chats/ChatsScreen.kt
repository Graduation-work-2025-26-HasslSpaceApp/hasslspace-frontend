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
import ru.hse.app.androidApp.ui.components.chats.mychats.MyChatsScreenContent
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.entity.model.chats.ChatsUiState
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadUserFriendsEvent
import ru.hse.app.androidApp.ui.navigation.NavigationItem

@Composable
fun ChatsScreen(
    navController: NavController,
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ChatsUiState.Loading -> {
                LoadingScreen()
            }

            is ChatsUiState.Error -> {
                ErrorScreen()
            }

            is ChatsUiState.Success -> {
                ChatsWithStateContent(
                    uiState = uiState as ChatsUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun ChatsWithStateContent(
    uiState: ChatsUiState.Success,
    navController: NavController,
    viewModel: ChatsViewModel
) {
    val context = LocalContext.current
    MyChatsScreenContent(
        imageLoader = viewModel.imageLoader,
        chats = uiState.data.chats,
        onAddClick = {
            navController.navigate(NavigationItem.NewMessageScreen.route)
        },
        onChatClick = { chat ->
            navController.navigate(NavigationItem.Chat.route + "/${chat.id}")
        },
        searchText = viewModel.searchText.value,
        onValueChange = viewModel::onSearchValueChange,
        isDarkTheme = viewModel.isDark
    )
}