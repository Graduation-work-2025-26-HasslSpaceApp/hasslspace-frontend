package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.ServerInfoExpandedDto

data class ServerInfoExpanded(
    val id: String,
    val name: String,
    val photoURL: String?,
    val members: List<ServerMember>,
    val isOwner: Boolean,
    val textChannels: List<TextChannel>,
    val voiceChannels: List<VoiceChannel>,
) {
    data class TextChannel(
        val id: String,
        val name: String,
    )

    data class VoiceChannel(
        val id: String,
        val name: String,
    )

    data class ServerMember(
        val id: String,
        val name: String,
        val username: String,
        val status: String,
        val photoURL: String?,
        val roles: List<ServerRole>? = null,
    ) {
        data class ServerRole(
            val id: String,
            val name: String,
            val color: String,
        )
    }
}

fun ServerInfoExpandedDto.toDomain(): ServerInfoExpanded {
    return ServerInfoExpanded(
        id = this.id,
        name = this.name,
        photoURL = this.photoURL,
        isOwner = this.isOwner,
        members = this.members.map { memberDto ->
            ServerInfoExpanded.ServerMember(
                id = memberDto.id,
                name = memberDto.name,
                username = memberDto.username,
                status = memberDto.status,
                photoURL = memberDto.photoURL,
                roles = memberDto.roles?.map { roleDto ->
                    ServerInfoExpanded.ServerMember.ServerRole(
                        id = roleDto.id,
                        name = roleDto.name,
                        color = roleDto.color
                    )
                }
            )
        },
        textChannels = this.textChannels.map { channelDto ->
            ServerInfoExpanded.TextChannel(
                id = channelDto.id,
                name = channelDto.name
            )
        },
        voiceChannels = this.voiceChannels.map { channelDto ->
            ServerInfoExpanded.VoiceChannel(
                id = channelDto.id,
                name = channelDto.name
            )
        }
    )
}