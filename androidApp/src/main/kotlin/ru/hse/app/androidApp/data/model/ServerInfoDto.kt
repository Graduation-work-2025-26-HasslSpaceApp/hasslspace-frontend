package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ServerInfoDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("usersCount")
    val usersCount: Int,

    @JsonProperty("photoUrl")
    val photoURL: String?,
)