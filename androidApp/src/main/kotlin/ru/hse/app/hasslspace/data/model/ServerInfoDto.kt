package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ServerInfoDto(
    @JsonProperty("serverId")
    val id: String,

    @JsonProperty("serverName")
    val name: String,

    @JsonProperty("photoUrl")
    val photoURL: String?,
)