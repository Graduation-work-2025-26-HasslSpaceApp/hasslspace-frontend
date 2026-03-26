package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateProfileDto(
    @JsonProperty("username")
    val username: String? = null,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("photoUrl")
    val photoUrl: String? = null,

    @JsonProperty("description")
    val description: String? = null,
)