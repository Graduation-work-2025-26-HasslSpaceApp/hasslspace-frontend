package ru.hse.app.androidApp.screen.servers

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
import ru.hse.app.androidApp.R
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.servers.members.AddMembersSheet
import ru.hse.app.androidApp.ui.components.servers.servercard.ChannelCardBottomSheet
import ru.hse.app.androidApp.ui.components.servers.servercard.ChannelsBottomSheet
import ru.hse.app.androidApp.ui.components.servers.servercard.ServerCardContent
import ru.hse.app.androidApp.ui.entity.model.servers.ServerCardUiState
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetFriendsNotInServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.SendServerInvitationEvent

@Composable
fun MainServerScreen(
    navController: NavController,
    serverId: String,
    viewModel: ServerCardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val getServerInfoEvent by viewModel.getServerInfoEvent.collectAsState()
    val getFriendsNotInServerEvent by viewModel.getFriendsNotInServerEvent.collectAsState()
    val sendServerInvitationEvent by viewModel.sendServerInvitationEvent.collectAsState()

    LaunchedEffect(serverId) {
        viewModel.getServerInfo(serverId)
    }

    LaunchedEffect(getServerInfoEvent) {
        when (getServerInfoEvent) {
            is GetServerInfoEvent.SuccessLoad -> {}

            is GetServerInfoEvent.Error -> {
                val message = (getServerInfoEvent as GetServerInfoEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetGetServerInfoEvent()
    }

    LaunchedEffect(getFriendsNotInServerEvent) {
        when (getFriendsNotInServerEvent) {
            is GetFriendsNotInServerEvent.SuccessLoad -> {}

            is GetFriendsNotInServerEvent.Error -> {
                val message =
                    (getFriendsNotInServerEvent as GetFriendsNotInServerEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetGetFriendsNotInServerEvent()
    }

    LaunchedEffect(sendServerInvitationEvent) {
        when (sendServerInvitationEvent) {
            is SendServerInvitationEvent.Success -> {
                val userId = (sendServerInvitationEvent as SendServerInvitationEvent.Success).userId

                viewModel.setSentInvitation(userId)
            }

            is SendServerInvitationEvent.Error -> {
                val message = (sendServerInvitationEvent as SendServerInvitationEvent.Error).message
                viewModel.showToast(message)
            }

            null -> {}
        }
        viewModel.resetGetFriendsNotInServerEvent()
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
                MainServerScreenWithStateContent(
                    uiState = uiState as ServerCardUiState.Success,
                    navController = navController,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun MainServerScreenWithStateContent(
    uiState: ServerCardUiState.Success,
    navController: NavController,
    viewModel: ServerCardViewModel,
) {
    val data = uiState.data
    val context = LocalContext.current

    ServerCardContent(
        onBackClick = { navController.popBackStack() },
        onServerNameClick = {/*todo*/ },
        onAddPeopleClick = {
            viewModel.getFriendsNotInServer(data.chosenServer.id)
            viewModel.showAddFriendsSheet.value = true
        },
        serverName = data.chosenServer.name,
        textChannels = data.chosenServer.textChannels,
        voiceChannels = data.chosenServer.voiceChannels,
        onTextChannelShortClick = {/*todo*/ },
        onTextChannelLongClick = {
            viewModel.chosenTextChannel.value = it
            viewModel.showTextChannelOptions.value = true
        },
        onVoiceChannelShortClick = {/*todo*/ },
        onVoiceChannelLongClick = {
            viewModel.chosenVoiceChannel.value = it
            viewModel.showVoiceChannelOptions.value = true
        },
        onTextChannelsClick = { viewModel.showTextChannelsSettings.value = true },
        onVoiceChannelsClick = { viewModel.showVoiceChannelsSettings.value = true },
        isDarkTheme = viewModel.isDarkTheme,
        isOwner = data.chosenServer.isOwner
    )

    if (viewModel.showAddFriendsSheet.value) {
        AddMembersSheet(
            imageLoader = context.imageLoader,
            friends = data.friendsNotInServer,
            onInviteClick = {
                viewModel.sendServerInvitation(
                    userId = it.id,
                    serverId = data.chosenServer.id
                )
            },
            isDarkTheme = viewModel.isDarkTheme,
            onDismiss = { viewModel.showAddFriendsSheet.value = false }
        )
    }

    if (viewModel.showTextChannelsSettings.value) {
        ChannelsBottomSheet(
            imageLoader = context.imageLoader,
            text = "Текстовые каналы",
            isDarkTheme = viewModel.isDarkTheme,
            serverPictureUrl = data.chosenServer.photoUrl,
            onReadClick = {/*todo*/ },
            onCreateChannel = {/*todo*/ },
            onDismiss = { viewModel.showTextChannelsSettings.value = false },
            isModerator = data.chosenServer.isOwner
        )
    }

    if (viewModel.showVoiceChannelsSettings.value) {
        ChannelsBottomSheet(
            imageLoader = context.imageLoader,
            text = "Голосовые каналы",
            isDarkTheme = viewModel.isDarkTheme,
            serverPictureUrl = data.chosenServer.photoUrl,
            onReadClick = {/*todo*/ },
            onCreateChannel = {/*todo*/ },
            onDismiss = { viewModel.showVoiceChannelsSettings.value = false },
            isModerator = data.chosenServer.isOwner
        )
    }

    if (viewModel.showTextChannelOptions.value && viewModel.chosenTextChannel.value != null) {
        ChannelCardBottomSheet(
            text = viewModel.chosenTextChannel.value!!.title,
            icon = R.drawable.hashtag,
            isDarkTheme = viewModel.isDarkTheme,
            onReadClick = {/*todo*/ },
            onSetUpChannel = {/*todo*/ },
            onDismiss = {
                viewModel.showTextChannelOptions.value = false
                viewModel.chosenTextChannel.value = null
            },
            isModerator = data.chosenServer.isOwner
        )
    }

    if (viewModel.showVoiceChannelOptions.value && viewModel.chosenVoiceChannel.value != null) {
        ChannelCardBottomSheet(
            text = viewModel.chosenVoiceChannel.value!!.title,
            icon = R.drawable.voice,
            isDarkTheme = viewModel.isDarkTheme,
            onReadClick = {/*todo*/ },
            onSetUpChannel = {/*todo*/ },
            onDismiss = {
                viewModel.showVoiceChannelOptions.value = false
                viewModel.chosenVoiceChannel.value = null
            },
            isModerator = data.chosenServer.isOwner
        )
    }
}