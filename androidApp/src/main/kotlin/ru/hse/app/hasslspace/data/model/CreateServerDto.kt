package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateServerDto(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("iconUrl")
    val iconUrl: String? = null,
)