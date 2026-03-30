package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateChannelDto(
    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("position")
    val position: Int? = null,

    @JsonProperty("max_members")
    val maxMembers: Int? = null,

    @JsonProperty("is_private")
    val isPrivate: Boolean? = null,
)