package ru.hse.app.androidApp.ui.entity.model.serversettings

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ru.hse.app.androidApp.domain.model.entity.ServerInfoExpanded
import ru.hse.app.androidApp.ui.entity.model.FriendCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.InvitationUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniCheckboxUiModel
import ru.hse.app.androidApp.ui.entity.model.RoleMiniUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.servers.ServerCardUiModel
import ru.hse.app.androidApp.ui.entity.model.toStatusPresentation

sealed interface ServerSettingsUiState {
    data object Loading : ServerSettingsUiState
    data class Success(val data: ServerSettingsUiModel) : ServerSettingsUiState
    data class Error(val message: String) : ServerSettingsUiState
}

data class ServerSettingsUiModel(
    val id: String,
    val name: String,
    val photoUrl: String,
    val isOwner: Boolean,
    val members: List<ServerMemberUiModel>,
    val roles: List<RoleInfoUiModel>,
    val invitations: List<InvitationUiModel>,

    val chosenRoles: List<RoleMiniCheckboxUiModel>,

    val newRole: NewRoleUiModel,

    val editedRole: EditRoleUiModel
) {
    data class NewRoleUiModel(
        val name: String,
        val color: Color,
        val members: List<FriendCheckboxUiModel>
    )

    data class EditRoleUiModel(
        val id: String,
        val name: String,
        val color: Color,
        val members: List<FriendCheckboxUiModel>
    )
}

fun ServerInfoExpanded.toServerSettingsUiModel(): ServerSettingsUiModel {
    return ServerSettingsUiModel(
        id = this.id,
        name = this.name,
        photoUrl = this.photoURL ?: "",
        isOwner = this.isOwner,
        members = this.members.map { member ->
            ServerMemberUiModel(
                id = member.id,
                name = member.name,
                nickname = member.username,
                status = member.status.toStatusPresentation(),
                avatarUrl = member.photoURL ?: "",
                mainRole = member.roles?.firstOrNull()?.let { role ->
                    RoleMiniUiModel(
                        id = role.id,
                        title = role.name,
                        color = Color(role.color.toColorInt())
                    )
                },
                allRoles = member.roles?.map { role ->
                    RoleMiniUiModel(
                        id = role.id,
                        title = role.name,
                        color = Color(role.color.toColorInt())
                    )
                } ?: emptyList(),
                isOwner = false //todo
            )
        },
        roles = listOf(),
        invitations = listOf(),
        chosenRoles = listOf(),
        newRole = ServerSettingsUiModel.NewRoleUiModel(
            name = "",
            color = Color.Transparent,
            members = this.members.map { member ->
                FriendCheckboxUiModel(
                    id = member.id,
                    name = member.name,
                    nickname = member.username,
                    status = member.status.toStatusPresentation(),
                    avatarUrl = member.photoURL?: "",
                    isChosen = false
                )
            }
        ),
        editedRole = ServerSettingsUiModel.EditRoleUiModel(
            id = "",
            name = "",
            color = Color.Transparent,
            members = this.members.map { member ->
                FriendCheckboxUiModel(
                    id = member.id,
                    name = member.name,
                    nickname = member.username,
                    status = member.status.toStatusPresentation(),
                    avatarUrl = member.photoURL?: "",
                    isChosen = false
                )
            }
        ),
    )
}