package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateRoleDto(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("color")
    val color: String,

    @JsonProperty("position")
    val position: Int = 1,
)