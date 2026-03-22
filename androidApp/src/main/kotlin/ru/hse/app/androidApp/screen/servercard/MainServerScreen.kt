package ru.hse.app.androidApp.screen.servercard

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
import ru.hse.app.androidApp.ui.components.common.dialog.RowButtonDialog
import ru.hse.app.androidApp.ui.components.common.error.ErrorScreen
import ru.hse.app.androidApp.ui.components.common.loading.LoadingScreen
import ru.hse.app.androidApp.ui.components.servers.configurechannel.ConfigureMembersAndRoles
import ru.hse.app.androidApp.ui.components.servers.configurechannel.ConfigureVoiceChannelContent
import ru.hse.app.androidApp.ui.components.servers.createchannel.CreateChannelContent
import ru.hse.app.androidApp.ui.components.servers.editserver.InfoServerBottomSheet
import ru.hse.app.androidApp.ui.components.servers.members.AddMembersSheet
import ru.hse.app.androidApp.ui.components.servers.servercard.ChannelCardBottomSheet
import ru.hse.app.androidApp.ui.components.servers.servercard.ChannelsBottomSheet
import ru.hse.app.androidApp.ui.components.servers.servercard.ServerCardContent
import ru.hse.app.androidApp.ui.entity.model.servers.ServerCardUiState
import ru.hse.app.androidApp.ui.entity.model.servers.events.CreateChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteChannelEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.DeleteServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetFriendsNotInServerEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.GetServerInfoEvent
import ru.hse.app.androidApp.ui.entity.model.servers.events.SendServerInvitationEvent
import ru.hse.app.androidApp.ui.navigation.NavigationItem

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
    val createChannelEvent by viewModel.createChannelEvent.collectAsState()

    val deleteServerEvent by viewModel.deleteServerEvent.collectAsState()
    val deleteChannelEvent by viewModel.deleteChannelEvent.collectAsState()

    LaunchedEffect(serverId) {
        viewModel.getServerInfo(serverId)
    }

    LaunchedEffect(deleteServerEvent) {
        when (deleteServerEvent) {
            is DeleteServerEvent.SuccessDelete -> {
                viewModel.showDeleteServerDialog.value = false
                viewModel.showServerSettingsSheet.value = false
                navController.navigate(NavigationItem.ServersMain.route) {
                    popUpTo(NavigationItem.ServersMain.route) { inclusive = true }
                }
            }

            is DeleteServerEvent.Error -> {
                val message = (deleteServerEvent as CreateChannelEvent.Error).message
                viewModel.showToast(message)
                viewModel.showDeleteServerDialog.value = false
                viewModel.showServerSettingsSheet.value = false
            }

            null -> {}
        }
        viewModel.resetDeleteServerEvent()
    }

    LaunchedEffect(deleteChannelEvent) {
        when (deleteChannelEvent) {
            is DeleteChannelEvent.SuccessDelete -> {
                viewModel.showDeleteChannel.value = false
                viewModel.showEditChannel.value = false
                viewModel.getServerInfo(serverId)
            }

            is DeleteChannelEvent.Error -> {
                val message = (deleteChannelEvent as DeleteChannelEvent.Error).message
                viewModel.showToast(message)
                viewModel.showDeleteChannel.value = false
                viewModel.showEditChannel.value = false
            }

            null -> {}
        }
        viewModel.resetDeleteChannelEvent()
    }

    LaunchedEffect(createChannelEvent) {
        when (createChannelEvent) {
            is CreateChannelEvent.SuccessCreate -> {
                viewModel.getServerInfo(serverId)
                viewModel.creatingChannel.value = false
            }

            is CreateChannelEvent.Error -> {
                val message = (createChannelEvent as CreateChannelEvent.Error).message
                viewModel.showToast(message)
                viewModel.creatingChannel.value = false
            }

            null -> {}
        }
        viewModel.resetCreateChannelEvent()
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

    if (!viewModel.creatingChannel.value) {
        ServerCardContent(
            onBackClick = { navController.popBackStack() },
            onServerNameClick = {
                viewModel.showServerSettingsSheet.value = true
            },
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
    }

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
            onCreateChannel = {
                viewModel.showTextChannelsSettings.value = false
                viewModel.creatingChannel.value = true
                viewModel.typeOfCreatingChannel.value = "text"
            },
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
            onCreateChannel = {
                viewModel.showVoiceChannelsSettings.value = false
                viewModel.creatingChannel.value = true
                viewModel.typeOfCreatingChannel.value = "voice"
            },
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
            onSetUpChannel = {
                viewModel.loadChosenChannel(
                    data.chosenServer,
                    viewModel.chosenTextChannel.value!!.id
                )
                viewModel.showTextChannelOptions.value = false
                viewModel.showEditChannel.value = true
            },
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
            onSetUpChannel = {
                viewModel.loadChosenChannel(
                    data.chosenServer,
                    viewModel.chosenVoiceChannel.value!!.id
                )
                viewModel.showVoiceChannelOptions.value = false
                viewModel.showEditChannel.value = true
            },
            onDismiss = {
                viewModel.showVoiceChannelOptions.value = false
                viewModel.chosenVoiceChannel.value = null
            },
            isModerator = data.chosenServer.isOwner
        )
    }

    if (viewModel.creatingChannel.value) {
        CreateChannelContent(
            onCloseClick = {
                viewModel.creatingChannel.value = false
                viewModel.newChannelName.value = ""
                viewModel.newChannelIsPrivate.value = false
                viewModel.limitNewChannel.floatValue = 0f
                viewModel.resetMembersAndRoles()
            },
            channelName = viewModel.newChannelName.value,
            onChannelNameChanged = viewModel::onNewChannelNameChanged,
            onCreateChannelClick = {
                viewModel.createChannel(
                    serverId = data.chosenServer.id,
                    channelName = viewModel.newChannelName.value,
                    isPrivate = viewModel.newChannelIsPrivate.value,
                    type = viewModel.typeOfCreatingChannel.value,
                    limit = viewModel.limitNewChannel.floatValue,
                    members = data.newChannelMembers,
                    roles = data.newChannelRoles
                )
                viewModel.resetMembersAndRoles()
            },
            type = viewModel.typeOfCreatingChannel.value,
            isDarkTheme = viewModel.isDarkTheme,
            isPrivate = viewModel.newChannelIsPrivate.value,
            onPrivateChange = viewModel::onNewChannelIsPrivate,
            onAddMembers = {
                viewModel.loadCheckboxRoles(data.chosenServer)
                viewModel.loadCheckboxFriends(data.chosenServer)
                viewModel.showChooseMembersAndRoles.value = true
            },
            sliderValue = viewModel.limitNewChannel.floatValue,
            onSliderValueChange = viewModel::onLimitValueChange
        )
    }

    if (viewModel.showEditChannel.value) {
        ConfigureVoiceChannelContent(
            onBackClick = {
                viewModel.showEditChannel.value = false
            },
            channelName = data.editChannel.name,
            onChannelNameChanged = viewModel::onEditChannelNameChanged,
            isDarkTheme = viewModel.isDarkTheme,
            isPrivate = data.editChannel.isPrivate,
            onPrivateChange = viewModel::onEditChannelIsPrivate,
            onAddMembers = {
                viewModel.showChooseMembersAndRolesEditChannel.value = true
            },
            onSaveClick = {/*todo*/ },
            onDeleteChannel = { viewModel.showDeleteChannel.value = true },
            sliderValue = data.editChannel.limit,
            onSliderValueChange = viewModel::onEditChannelLimitValueChange,
            type = data.editChannel.type
        )
    }

    if (viewModel.showChooseMembersAndRoles.value) {
        ConfigureMembersAndRoles(
            imageLoader = context.imageLoader,
            friends = data.newChannelMembers,
            roles = data.newChannelRoles,
            onBackClick = { viewModel.showChooseMembersAndRoles.value = false },
            onSaveClick = { viewModel.showChooseMembersAndRoles.value = false },
            onToggleRole = { viewModel.onToggleRole(it) },
            onToggleFriend = { viewModel.onToggleFriend(it) },
            isDarkTheme = viewModel.isDarkTheme
        )
    }

    if (viewModel.showChooseMembersAndRolesEditChannel.value) {
        ConfigureMembersAndRoles(
            imageLoader = context.imageLoader,
            friends = data.editChannel.members,
            roles = data.editChannel.roles,
            onBackClick = { viewModel.showChooseMembersAndRolesEditChannel.value = false },
            onSaveClick = { viewModel.showChooseMembersAndRolesEditChannel.value = false },
            onToggleRole = { viewModel.onToggleRoleEditChannel(it) },
            onToggleFriend = { viewModel.onToggleFriendEditChannel(it) },
            isDarkTheme = viewModel.isDarkTheme
        )
    }

    if (viewModel.showServerSettingsSheet.value) {
        InfoServerBottomSheet(
            imageLoader = context.imageLoader,
            serverName = data.chosenServer.name,
            serverAvatarUrl = data.chosenServer.photoUrl,
            countOfMembers = data.chosenServer.members.count(),
            onInviteClick = {
                viewModel.getFriendsNotInServer(data.chosenServer.id)
                viewModel.showServerSettingsSheet.value = false
                viewModel.showAddFriendsSheet.value = true
            },
            onSettingsClick = {
                navController.navigate(NavigationItem.ServerSettings.route + "/${data.chosenServer.id}")
                viewModel.showServerSettingsSheet.value = false
            },
            onDeleteClick = { viewModel.showDeleteServerDialog.value = true },
            isDarkTheme = viewModel.isDarkTheme,
            onDismiss = { viewModel.showServerSettingsSheet.value = false },
            onMembersClick = {
                navController.navigate(NavigationItem.ServerMembersInfo.route + "/${data.chosenServer.id}")
                viewModel.showServerSettingsSheet.value = false
            },
            isOwner = data.chosenServer.isOwner
        )
    }

    if (viewModel.showDeleteChannel.value) {
        RowButtonDialog(
            showDialog = viewModel.showDeleteChannel,
            questionText = "Вы действительно хотите удалить канал?",
            apply = "Удалить",
            dismiss = "Оставить",
            onApplyClick = { viewModel.deleteChannel(data.chosenServer.id, data.editChannel.id) },
            onDismissClick = { viewModel.showDeleteChannel.value = false }
        )
    }

    if (viewModel.showDeleteServerDialog.value) {
        RowButtonDialog(
            showDialog = viewModel.showDeleteServerDialog,
            questionText = "Вы действительно хотите удалить сервер?",
            apply = "Удалить",
            dismiss = "Оставить",
            onApplyClick = { viewModel.deleteServer(data.chosenServer.id) },
            onDismissClick = { viewModel.showDeleteServerDialog.value = false }
        )
    }
}