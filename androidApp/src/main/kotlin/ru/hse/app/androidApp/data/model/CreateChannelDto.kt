package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateChannelDto(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("isPrivate")
    val isPrivate: Boolean = false,

    @JsonProperty("type")
    val type: String = "TEXT",

    @JsonProperty("maxMembers")
    val limit: Int? = null,

    @JsonProperty("members")
    val members: List<String>,

    @JsonProperty("roles")
    val roles: List<String>,
)