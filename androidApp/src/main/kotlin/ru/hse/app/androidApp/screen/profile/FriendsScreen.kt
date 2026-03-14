package ru.hse.app.androidApp.screen.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import ru.hse.app.androidApp.ui.components.profile.friends.FriendsContent
import ru.hse.app.androidApp.ui.components.userinfocard.CommonServersBottomSheet
import ru.hse.app.androidApp.ui.components.userinfocard.UserInfoBottomSheet
import ru.hse.app.androidApp.ui.entity.model.profile.ProfileUiState
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadChosenUserCommonServersEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadChosenUserEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.RespondToFriendRequestEvent
import ru.hse.app.androidApp.ui.navigation.NavigationItem

@Composable
fun FriendsScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val loadChosenUserEvent by viewModel.loadChosenUserEvent.collectAsState()
    val loadChosenUserCommonServersEvent by viewModel.loadChosenUserCommonServersEvent.collectAsState()
    val respondToFriendRequestEvent by viewModel.respondToFriendshipRequestEvent.collectAsState()

    LaunchedEffect(respondToFriendRequestEvent) {
        when (respondToFriendRequestEvent) {
            is RespondToFriendRequestEvent.SuccessRespond -> {
                viewModel.loadUserFriends()
                viewModel.showToast("Успешно")
            }

            is RespondToFriendRequestEvent.Error -> {
                val message =
                    (respondToFriendRequestEvent as RespondToFriendRequestEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetRespondToFriendRequestEvent()
    }

    LaunchedEffect(loadChosenUserEvent) {
        when (loadChosenUserEvent) {
            is LoadChosenUserEvent.SuccessLoad -> {
                viewModel.showFriendCard.value = true
            }

            is LoadChosenUserEvent.Error -> {
                val message = (loadChosenUserEvent as LoadChosenUserEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetLoadChosenUserEvent()
    }

    LaunchedEffect(loadChosenUserCommonServersEvent) {
        when (loadChosenUserCommonServersEvent) {
            is LoadChosenUserCommonServersEvent.SuccessLoad -> {}

            is LoadChosenUserCommonServersEvent.Error -> {
                val message =
                    (loadChosenUserCommonServersEvent as LoadChosenUserCommonServersEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetLoadChosenUserCommonServersEvent()
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
                FriendsScreenWithStateContent(
                    uiState = uiState as ProfileUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun FriendsScreenWithStateContent(
    uiState: ProfileUiState.Success,
    navController: NavController,
    viewModel: ProfileViewModel,
) {
    val data = uiState.data
    val context = LocalContext.current

    FriendsContent(
        imageLoader = context.imageLoader,
        friends = viewModel.getFriends(data.friends),
        applications = viewModel.getIncomingRequests(data.friends),
        onBackClick = { navController.popBackStack() },
        onAddClick = { navController.navigate(NavigationItem.AddFriends.route) },
        onFriendClick = {
            viewModel.loadChosenUserCommonServers(userId = it.id)
            viewModel.loadChosenUser(userId = it.id)
        },
        onCallClick = { viewModel.onCallClick(userId = it.id) },
        onMessageClick = { viewModel.onMessageClick(userId = it.id) },
        onApplicationClick = {
            viewModel.loadChosenUserCommonServers(userId = it.id)
            viewModel.loadChosenUser(userId = it.id)
        },
        onAcceptClick = { viewModel.respondFriend(it, "ACCEPTED") },
        onDismissClick = { viewModel.respondFriend(it, "DECLINED") },
        searchText = viewModel.searchValueFriends.value,
        onValueChange = viewModel::onSearchValueChange,
        isDarkTheme = data.isDarkCheck
    )

    if (viewModel.showFriendCard.value && data.chosenUser != null) {
        UserInfoBottomSheet(
            isDarkTheme = data.isDarkCheck,
            profilePictureUrl = data.chosenUser.avatarUrl,
            imageLoader = context.imageLoader,
            status = data.chosenUser.status,
            username = data.chosenUser.name,
            nickname = data.chosenUser.nickname,
            commonServersCount = data.chosenUserCommonServers.size,
            onCommonServersClick = {
                if (data.chosenUserCommonServers.isNotEmpty()) {
                    viewModel.showCommonServers.value = true
                }
            },
            onMessageClick = { viewModel.onMessageClick(userId = data.chosenUser.id) },
            onCallClick = { viewModel.onCallClick(userId = data.chosenUser.id) },
            onVideoCallClick = { viewModel.onVideoCallClick(userId = data.chosenUser.id) },
            aboutUserInfo = data.chosenUser.description,
            onDismiss = { viewModel.showFriendCard.value = false },
            onInvite = { /*todo*/ },
            onCopyNickname = {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied Text", data.chosenUser.nickname)
                clipboard.setPrimaryClip(clip)

                viewModel.showToast("Текст скопирован в буфер обмена")
            },
            onThirdOptionClick = { /*todo*/ },
            type = data.chosenUser.type
        )
    }

    if (viewModel.showCommonServers.value) {
        CommonServersBottomSheet(
            imageLoader = context.imageLoader,
            servers = data.chosenUserCommonServers,
            onServerClick = { server -> navController.navigate(NavigationItem.MainServerScreen.route + "/${server.id}") },
            isDarkTheme = data.isDarkCheck,
            onDismiss = { viewModel.showCommonServers.value = false }
        )
    }
}
