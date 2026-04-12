package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateServerDto(
    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("iconUrl")
    val iconUrl: String? = null,
)