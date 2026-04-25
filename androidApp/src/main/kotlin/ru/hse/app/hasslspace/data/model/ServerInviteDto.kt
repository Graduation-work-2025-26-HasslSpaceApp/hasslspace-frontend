package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ServerInviteDto(
    @JsonProperty("code")
    val code: String,

    @JsonProperty("serverId")
    val serverId: String,

    @JsonProperty("creatorId")
    val creatorId: String,

    @JsonProperty("expiresAt")
    val expiresAt: LocalDateTime,

    @JsonProperty("serverName")
    val serverName: String? = null,

    @JsonProperty("creatorName")
    val creatorName: String? = null,

    @JsonProperty("inviteUrl")
    val inviteUrl: String? = null
)