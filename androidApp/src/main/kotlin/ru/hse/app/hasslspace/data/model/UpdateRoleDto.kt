package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateRoleDto(
    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("color")
    val color: String? = null,

    @JsonProperty("position")
    val position: Int? = null,
)