package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateServerDto (
    @JsonProperty("name")
    val name: String,

    @JsonProperty("photoUrl")
    val photoUrl: String? = null,
)