package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateChannelDto(
    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("position")
    val position: Int? = null,

    @get:JsonProperty("maxMembers")
    val maxMembers: Int? = null,

    @get:JsonProperty("isPrivate")
    val isPrivate: Boolean? = null,
)