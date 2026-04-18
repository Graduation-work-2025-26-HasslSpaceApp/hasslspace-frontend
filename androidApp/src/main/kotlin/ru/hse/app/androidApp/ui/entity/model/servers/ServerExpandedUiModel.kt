package ru.hse.app.androidApp.ui.entity.model.servers

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import ru.hse.app.androidApp.domain.model.entity.ServerInfoExpanded
import ru.hse.app.androidApp.ui.entity.model.RoleMiniUiModel
import ru.hse.app.androidApp.ui.entity.model.ServerMemberUiModel
import ru.hse.app.androidApp.ui.entity.model.TextChannelUiModel
import ru.hse.app.androidApp.ui.entity.model.VoiceChannelUiModel
import ru.hse.app.androidApp.ui.entity.model.toStatusPresentation

data class ServerExpandedUiModel(
    val id: String,
    val name: String,
    val photoUrl: String,
    val isOwner: Boolean,
    val members: List<ServerMemberUiModel>,
    val textChannels: List<TextChannelUiModel>,
    val voiceChannels: List<VoiceChannelUiModel>
)

fun ServerInfoExpanded.toUi(): ServerExpandedUiModel {
    return ServerExpandedUiModel(
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
                isOwner = false
            )
        },
        textChannels = this.textChannels.map { channel ->
            TextChannelUiModel(
                id = channel.id,
                title = channel.name
            )
        },
        voiceChannels = this.voiceChannels.map { channel ->
            VoiceChannelUiModel(
                id = channel.id,
                title = channel.name
            )
        }
    )
}