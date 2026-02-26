package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateRoleDto (
    @JsonProperty("name")
    val name: String,

    @JsonProperty("color")
    val color: String,

    @JsonProperty("members")
    val members: List<String>,
)