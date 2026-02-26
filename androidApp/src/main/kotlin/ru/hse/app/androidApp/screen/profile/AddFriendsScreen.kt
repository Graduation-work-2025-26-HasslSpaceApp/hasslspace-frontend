package ru.hse.app.androidApp.screen.profile

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
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.profile.addfriens.AddFriendsScreenContent
import ru.hse.app.androidApp.ui.entity.model.profile.events.CreateFriendRequestEvent
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState

@Composable
fun AddFriendsScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val createFriendRequestEvent by viewModel.createFriendRequestEvent.collectAsState()

    LaunchedEffect(createFriendRequestEvent) {
        when (createFriendRequestEvent) {
            is CreateFriendRequestEvent.SuccessRequest -> {
                val nickname =
                    (createFriendRequestEvent as CreateFriendRequestEvent.SuccessRequest).nickname
                viewModel.errorAddFriends.value = false
                viewModel.infoTextAddFriend.value =
                    "Получилось! Отправили заявку в друзья пользователю $nickname"
            }

            is CreateFriendRequestEvent.Error -> {
                viewModel.errorAddFriends.value = true
                viewModel.infoTextAddFriend.value =
                    "Хм... Не получилось. Проверьте, что вы ввели правильное имя пользователя"
            }

            null -> {}
        }
        viewModel.resetCreateFriendRequestEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ProfileUiState.Loading -> {
                LoadingScreen()
            }

            is ProfileUiState.Error -> {
                ErrorScreen()
            }

            is ProfileUiState.Success -> {
                AddFriendsScreenWithStateContent(
                    uiState = uiState as ProfileUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun AddFriendsScreenWithStateContent(
    uiState: ProfileUiState.Success,
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val data = uiState.data
    val context = LocalContext.current

    AddFriendsScreenContent(
        imageLoader = context.imageLoader,
        requests = viewModel.getOutgoingRequests(data.friends),
        onBackClick = { navController.popBackStack() },
        onRequestClick = {
            viewModel.loadChosenUserCommonServers(userId = it.id)
            viewModel.loadChosenUser(userId = it.id)
        },
        searchText = viewModel.addFriendFieldValue.value,
        onValueChange = viewModel::changeAddFriendValue,
        onSendClick = { viewModel.createFriendshipRequest(viewModel.addFriendFieldValue.value) },
        onUndoClick = { viewModel.deleteFriendship(it.id) },
        infoText = viewModel.infoTextAddFriend.value,
        error = viewModel.errorAddFriends.value,
        isDarkTheme = data.isDarkCheck
    )
}