package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateServerDto(
    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("photoUrl")
    val photoUrl: String? = null,
)