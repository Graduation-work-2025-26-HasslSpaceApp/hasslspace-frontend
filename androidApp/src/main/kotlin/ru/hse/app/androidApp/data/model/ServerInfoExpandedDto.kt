package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ServerInfoExpandedDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("photoUrl")
    val photoURL: String?,

    @JsonProperty("members")
    val members: List<ServerMemberDto>,

    @JsonProperty("isOwner")
    val isOwner: Boolean,

    @JsonProperty("textChannels")
    val textChannels: List<TextChannelDto>,

    @JsonProperty("voiceChannels")
    val voiceChannels: List<VoiceChannelDto>,
) {
    data class TextChannelDto(
        @JsonProperty("id")
        val id: String,

        @JsonProperty("name")
        val name: String,
    )

    data class VoiceChannelDto(
        @JsonProperty("id")
        val id: String,

        @JsonProperty("name")
        val name: String,
    )

    data class ServerMemberDto(
        @JsonProperty("id")
        val id: String,

        @JsonProperty("name")
        val name: String,

        @JsonProperty("username")
        val username: String,

        @JsonProperty("status")
        val status: String,

        @JsonProperty("photoUrl")
        val photoURL: String?,

        @JsonProperty("roles")
        val roles: List<ServerRoleDto>? = null,
    ) {
        data class ServerRoleDto(
            @JsonProperty("id")
            val id: String,

            @JsonProperty("name")
            val name: String,

            @JsonProperty("color")
            val color: String,
        )
    }
}