package ru.hse.app.androidApp.ui.entity.model.servers

import androidx.compose.runtime.Immutable
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.FriendExtendedInfoUiModel
import ru.hse.app.androidApp.ui.entity.model.FriendUiModelInvitation
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerShortUiModel

sealed interface ServerCardUiState {
    data object Loading : ServerCardUiState
    data class Success(val data: ServerCardUiModel) : ServerCardUiState
    data class Error(val message: String) : ServerCardUiState
}

//TODO
@Immutable
data class ServerCardUiModel(
    val chosenServer: ServerExpandedUiModel,
    val friendsNotInServer: List<FriendUiModelInvitation>,

    val newChannelMembers: List<FriendCheckboxUiModel>,
    val newChannelRoles: List<RoleMiniCheckboxUiModel>,

    val chosenUser: FriendExtendedInfoUiModel?,
    val chosenUserCommonServers: List<ServerShortUiModel>,

    val editChannel: EditChannelUiModel
) {
    data class EditChannelUiModel(
        val id: String,
        val type: String = "text",
        val name: String,
        val isPrivate: Boolean,
        val limit: Float = 0f,
        val members: List<FriendCheckboxUiModel>,
        val roles: List<RoleMiniCheckboxUiModel>
    )
}