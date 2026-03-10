package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateChannelDto(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("is_private")
    val isPrivate: Boolean = false,

    @JsonProperty("type")
    val type: String = "text",

    @JsonProperty("limit")
    val limit: Int? = null,

    @JsonProperty("members")
    val members: List<String>,

    @JsonProperty("roles")
    val roles: List<String>,
)