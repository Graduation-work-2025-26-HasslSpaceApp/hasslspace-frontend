package ru.hse.app.androidApp.screen.servercard

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
import ru.hse.app.androidApp.ui.components.servers.members.ServerMembersContent
import ru.hse.app.androidApp.ui.components.userinfocard.CommonServersBottomSheet
import ru.hse.app.androidApp.ui.components.userinfocard.UserInfoBottomSheet
import ru.hse.app.androidApp.ui.entity.model.TypeUiModel
import ru.hse.app.androidApp.ui.entity.model.call.events.GetTokenEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.CreateFriendRequestEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.DeleteFriendshipEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadChosenUserCommonServersEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.LoadChosenUserEvent
import ru.hse.app.androidApp.ui.entity.model.profile.events.RespondToFriendRequestEvent
import ru.hse.app.androidApp.ui.entity.model.servers.ServerCardUiState
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.navigation.NavigationItem

@Composable
fun ServerMembersInfoScreen(
    navController: NavController,
    serverId: String,
    viewModel: ServerCardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val getServerInfoEvent by viewModel.getServerInfoEvent.collectAsState()
    val loadChosenUserEvent by viewModel.loadChosenUserEvent.collectAsState()
    val loadChosenUserCommonServersEvent by viewModel.loadChosenUserCommonServersEvent.collectAsState()
    val createFriendRequestEvent by viewModel.createFriendRequestEvent.collectAsState()
    val deleteFriendshipEvent by viewModel.deleteFriendshipEvent.collectAsState()
    val respondToFriendRequestEvent by viewModel.respondToFriendshipRequestEvent.collectAsState()
    val getTokenEvent by viewModel.getTokenEvent.collectAsState()

    LaunchedEffect(serverId) {
        viewModel.getServerInfo(serverId)
    }

    LaunchedEffect(getTokenEvent) {
        when (getTokenEvent) {
            is GetTokenEvent.Success -> {
                val token = (getTokenEvent as GetTokenEvent.Success).token
                val roomName = (getTokenEvent as GetTokenEvent.Success).roomName
                val videoEnabled = (getTokenEvent as GetTokenEvent.Success).videoEnabled

                viewModel.showFriendCard.value = false

                navController.navigate(NavigationItem.VoiceRoom.route + "/$token/$roomName/$videoEnabled")
            }

            is GetTokenEvent.Error -> {
                val message = (getTokenEvent as GetTokenEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetGetTokenEvent()
    }

    LaunchedEffect(respondToFriendRequestEvent) {
        when (respondToFriendRequestEvent) {
            is RespondToFriendRequestEvent.SuccessRespond -> {
                viewModel.handleError("Успешно")
            }

            is RespondToFriendRequestEvent.Error -> {
                val message =
                    (respondToFriendRequestEvent as RespondToFriendRequestEvent.Error).message
                viewModel.handleError(message)
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
                viewModel.handleError(message)
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
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetLoadChosenUserCommonServersEvent()
    }

    LaunchedEffect(createFriendRequestEvent) {
        when (createFriendRequestEvent) {
            is CreateFriendRequestEvent.SuccessRequest -> {}

            is CreateFriendRequestEvent.Error -> {
                val message =
                    (deleteFriendshipEvent as DeleteFriendshipEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetCreateFriendRequestEvent()
    }

    LaunchedEffect(deleteFriendshipEvent) {
        when (deleteFriendshipEvent) {
            is DeleteFriendshipEvent.SuccessDelete -> {}

            is DeleteFriendshipEvent.Error -> {
                val message =
                    (deleteFriendshipEvent as DeleteFriendshipEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetDeleteFriendshipEvent()
    }

    LaunchedEffect(getServerInfoEvent) {
        when (getServerInfoEvent) {
            is GetServerInfoEvent.SuccessLoad -> {}

            is GetServerInfoEvent.Error -> {
                val message = (getServerInfoEvent as GetServerInfoEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetGetServerInfoEvent()
    }

    LaunchedEffect(loadChosenUserEvent) {
        when (loadChosenUserEvent) {
            is LoadChosenUserEvent.SuccessLoad -> {
                viewModel.showFriendCard.value = true
            }

            is LoadChosenUserEvent.Error -> {
                val message = (loadChosenUserEvent as LoadChosenUserEvent.Error).message
                viewModel.handleError(message)
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
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetLoadChosenUserCommonServersEvent()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is ServerCardUiState.Loading -> {
                LoadingScreen()
            }

            is ServerCardUiState.Error -> {
                ErrorScreen()
            }

            is ServerCardUiState.Success -> {
                ServerMembersInfoScreenWithStateContent(
                    uiState = uiState as ServerCardUiState.Success,
                    serverId = serverId,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun ServerMembersInfoScreenWithStateContent(
    uiState: ServerCardUiState.Success,
    serverId: String,
    navController: NavController,
    viewModel: ServerCardViewModel,
) {
    val data = uiState.data
    val context = LocalContext.current

    ServerMembersContent(
        imageLoader = viewModel.imageLoader,
        friends = viewModel.searchedMembers,
        onBackClick = { navController.popBackStack() },
        onFriendClick = {
            viewModel.loadChosenUserCommonServers(userId = it.id)
            viewModel.loadChosenUser(userId = it.id)
        },
        searchText = viewModel.searchMembersText.value,
        onValueChange = viewModel::onSearchMembersText,
        isDarkTheme = viewModel.isDarkTheme
    )

    if (viewModel.showFriendCard.value && data.chosenUser != null) {
        UserInfoBottomSheet(
            isDarkTheme = viewModel.isDarkTheme,
            profilePictureUrl = data.chosenUser.avatarUrl,
            imageLoader = viewModel.imageLoader,
            status = data.chosenUser.status,
            username = data.chosenUser.name,
            nickname = data.chosenUser.nickname,
            commonServersCount = data.chosenUserCommonServers.size,
            onCommonServersClick = {
                if (data.chosenUserCommonServers.isNotEmpty()) {
                    viewModel.showCommonServers.value = true
                }
            },
            onMessageClick = { /* todo viewModel.onMessageClick(userId = data.chosenUser.id) */ },
            onCallClick = {
                viewModel.onCallClick(
                    memberName = data.currentUser?.name ?: "Unknown",
                    roomName = "Звонок с " + data.chosenUser.name,
                    friendshipId = data.chosenUser.friendshipId
                )
            },
            onVideoCallClick = {
                viewModel.onVideoCallClick(
                    memberName = data.currentUser?.name ?: "Unknown",
                    roomName = "Звонок с " + data.chosenUser.name,
                    friendshipId = data.chosenUser.friendshipId
                )
            },
            aboutUserInfo = data.chosenUser.description,
            onDismiss = { viewModel.showFriendCard.value = false },
            onInvite = { /*todo*/ },
            onCopyNickname = { //todo зачем копировать если поиска пока нет
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied Text", data.chosenUser.nickname)
                clipboard.setPrimaryClip(clip)

                viewModel.handleError("Текст скопирован в буфер обмена")
            },
            onThirdOptionClick = {
                when (data.chosenUser.type) {
                    TypeUiModel.FRIEND -> {
                        // todo показать окно-подтверждение
                        viewModel.deleteFriendship(data.chosenUser.id)
                    }

                    TypeUiModel.OUTGOING_REQUEST -> {
                        viewModel.deleteFriendship(data.chosenUser.id)
                    }

                    TypeUiModel.INCOMING_REQUEST -> {
                        viewModel.respondFriend(data.chosenUser.id, "ACCEPTED")
                    }

                    TypeUiModel.NONE -> {
                        viewModel.createFriendshipRequest(data.chosenUser.nickname)
                    }

                    TypeUiModel.BLOCKED -> {}
                }
            },
            type = data.chosenUser.type
        )
    }

    if (viewModel.showCommonServers.value) {
        CommonServersBottomSheet(
            imageLoader = viewModel.imageLoader,
            servers = data.chosenUserCommonServers,
            onServerClick = { server -> navController.navigate(NavigationItem.MainServerScreen.route + "/${server.id}") },
            isDarkTheme = viewModel.isDarkTheme,
            onDismiss = { viewModel.showCommonServers.value = false }
        )
    }
}