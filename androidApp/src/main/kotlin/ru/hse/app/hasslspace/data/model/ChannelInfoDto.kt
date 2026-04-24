package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ChannelInfoDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("isPrivate")
    val isPrivate: Boolean = false,

    @JsonProperty("type")
    val type: String = "TEXT",

    @JsonProperty("maxMembers")
    val maxMembers: Int? = null,

    @JsonProperty("position")
    val position: Int = 1,

    )