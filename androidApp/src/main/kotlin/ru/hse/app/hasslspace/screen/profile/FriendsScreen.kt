package ru.hse.app.hasslspace.screen.profile

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
import ru.hse.app.hasslspace.ui.components.common.error.ErrorScreen
import ru.hse.app.hasslspace.ui.components.common.loading.LoadingScreen
import ru.hse.app.hasslspace.ui.components.profile.friends.FriendsContent
import ru.hse.app.hasslspace.ui.components.userinfocard.CommonServersBottomSheet
import ru.hse.app.hasslspace.ui.components.userinfocard.UserInfoBottomSheet
import ru.hse.app.hasslspace.ui.entity.model.TypeUiModel
import ru.hse.app.hasslspace.ui.entity.model.call.events.GetTokenEvent
import ru.hse.app.hasslspace.ui.entity.model.chats.events.StartChatEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.ProfileUiState
import ru.hse.app.hasslspace.ui.entity.model.profile.events.CreateFriendRequestEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.events.DeleteFriendshipEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.events.LoadChosenUserCommonServersEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.events.LoadChosenUserEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.events.LoadUserDataEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.events.LoadUserFriendsEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.events.LoadUserServersEvent
import ru.hse.app.hasslspace.ui.entity.model.profile.events.RespondToFriendRequestEvent
import ru.hse.app.hasslspace.ui.navigation.NavigationItem

@Composable
fun FriendsScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val createFriendRequestEvent by viewModel.createFriendRequestEvent.collectAsState()
    val deleteFriendshipEvent by viewModel.deleteFriendshipEvent.collectAsState()
    val loadUserDataEvent by viewModel.loadUserInfoEvent.collectAsState()
    val loadUserFriendsEvent by viewModel.loadUserFriendsEvent.collectAsState()
    val loadUserServersEvent by viewModel.loadUserServersEvent.collectAsState()
    val loadChosenUserEvent by viewModel.loadChosenUserEvent.collectAsState()
    val loadChosenUserCommonServersEvent by viewModel.loadChosenUserCommonServersEvent.collectAsState()
    val respondToFriendRequestEvent by viewModel.respondToFriendshipRequestEvent.collectAsState()
    val getTokenEvent by viewModel.getTokenEvent.collectAsState()
    val startChatEvent by viewModel.startChatEvent.collectAsState()

    LaunchedEffect(startChatEvent) {
        when (startChatEvent) {
            is StartChatEvent.Success -> {
                val chatId = (startChatEvent as StartChatEvent.Success).chatId

                navController.navigate(NavigationItem.Chat.route + "/${chatId}")

            }

            is StartChatEvent.Error -> {
                val message = (startChatEvent as StartChatEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetStartChatEvent()
    }


    LaunchedEffect(Unit) {
        viewModel.loadUserData()
    }

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

    LaunchedEffect(loadUserFriendsEvent) {
        when (loadUserFriendsEvent) {
            is LoadUserFriendsEvent.SuccessLoad -> {}

            is LoadUserFriendsEvent.Error -> {
                val message =
                    (loadUserFriendsEvent as LoadUserFriendsEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetLoadUserFriendsEvent()
    }

    LaunchedEffect(loadUserServersEvent) {
        when (loadUserServersEvent) {
            is LoadUserServersEvent.SuccessLoad -> {}

            is LoadUserServersEvent.Error -> {
                val message =
                    (loadUserServersEvent as LoadUserServersEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetLoadUserServersEvent()
    }

    LaunchedEffect(loadUserDataEvent) {
        when (loadUserDataEvent) {
            is LoadUserDataEvent.SuccessLoad -> {}

            is LoadUserDataEvent.Error -> {
                val message =
                    (loadUserDataEvent as LoadUserDataEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetLoadUserInfoEvent()
    }

    LaunchedEffect(respondToFriendRequestEvent) {
        when (respondToFriendRequestEvent) {
            is RespondToFriendRequestEvent.SuccessRespond -> {
                viewModel.handleError("Успешно")
                viewModel.loadUserFriends()
            }

            is RespondToFriendRequestEvent.Error -> {
                val message =
                    (respondToFriendRequestEvent as RespondToFriendRequestEvent.Error).message
                viewModel.handleError(message)
                viewModel.loadUserFriends()
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
            is CreateFriendRequestEvent.SuccessRequest -> {
                val nickname =
                    (createFriendRequestEvent as CreateFriendRequestEvent.SuccessRequest).nickname
                viewModel.errorAddFriends.value = false
                viewModel.infoTextAddFriend.value =
                    "Получилось! Отправили заявку в друзья пользователю $nickname"
                viewModel.loadUserFriends()
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

    LaunchedEffect(deleteFriendshipEvent) {
        when (deleteFriendshipEvent) {
            is DeleteFriendshipEvent.SuccessDelete -> {
                viewModel.loadUserFriends()
            }

            is DeleteFriendshipEvent.Error -> {
                val message =
                    (deleteFriendshipEvent as DeleteFriendshipEvent.Error).message
                viewModel.handleError(message)
            }

            null -> {}
        }
        viewModel.resetDeleteFriendshipEvent()
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
    val isDark by viewModel.isDark.collectAsState()

    FriendsContent(
        imageLoader = viewModel.imageLoader,
        friends = viewModel.getFriends(data.friends),
        applications = viewModel.getIncomingRequests(data.friends),
        onBackClick = { navController.popBackStack() },
        onAddClick = { navController.navigate(NavigationItem.AddFriends.route) },
        onFriendClick = {
            viewModel.loadChosenUserCommonServers(userId = it.id)
            viewModel.loadChosenUser(userId = it.id)
        },
//        onCallClick = { viewModel.onCallClick(userId = it.id) },
//        onMessageClick = { viewModel.onMessageClick(userId = it.id) },
        onApplicationClick = {
            viewModel.loadChosenUserCommonServers(userId = it.id)
            viewModel.loadChosenUser(userId = it.id)
        },
        onAcceptClick = { viewModel.respondFriend(it.id, "ACCEPTED") },
        onDismissClick = { viewModel.respondFriend(it.id, "DECLINED") },
        searchText = viewModel.searchValueFriends.value,
        onValueChange = viewModel::onSearchValueChange,
        isDarkTheme = isDark
    )

    if (viewModel.showFriendCard.value && data.chosenUser != null) {
        UserInfoBottomSheet(
            isDarkTheme = isDark,
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
            onMessageClick = { viewModel.onMessageClick(userId = data.chosenUser.id) },
            onCallClick = {
                viewModel.onCallClick(
                    targetUserId = data.chosenUser.id,
                    memberName = data.name,
                    roomName = "Звонок с " + data.chosenUser.name
                )
            },
            onVideoCallClick = {
                viewModel.onVideoCallClick(
                    targetUserId = data.chosenUser.id,
                    memberName = data.name,
                    roomName = "Звонок с " +
                            data.chosenUser.name
                )
            },
            aboutUserInfo = data.chosenUser.description,
            onDismiss = { viewModel.showFriendCard.value = false },
            onInvite = {},
            onCopyNickname = {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied Text", data.chosenUser.nickname)
                clipboard.setPrimaryClip(clip)

                viewModel.handleError("Текст скопирован в буфер обмена")
            },
            onThirdOptionClick = {
                when (data.chosenUser.type) {
                    TypeUiModel.FRIEND -> {
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
            onDismissFriendRequest = { viewModel.respondFriend(data.chosenUser.id, "DECLINED") },
            type = data.chosenUser.type,
            extractMainColor = viewModel::extractMainColor
        )
    }

    if (viewModel.showCommonServers.value) {
        CommonServersBottomSheet(
            imageLoader = viewModel.imageLoader,
            servers = data.chosenUserCommonServers,
            onServerClick = { server -> navController.navigate(NavigationItem.MainServerScreen.route + "/${server.id}") },
            isDarkTheme = isDark,
            onDismiss = { viewModel.showCommonServers.value = false }
        )
    }
}
