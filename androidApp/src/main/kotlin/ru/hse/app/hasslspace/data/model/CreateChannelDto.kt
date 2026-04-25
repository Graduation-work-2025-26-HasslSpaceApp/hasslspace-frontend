package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateChannelDto(
    @JsonProperty("name")
    val name: String,

    @get:JsonProperty("isPrivate")
    val isPrivate: Boolean = false,

    @JsonProperty("type")
    val type: String = "TEXT",

    @JsonProperty("max_members")
    val maxMembers: Int? = null,

    @JsonProperty("position")
    val position: Int = 1,
)