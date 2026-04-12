package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("username")
    val nickname: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("status")
    val status: String,

    @JsonProperty("photoUrl")
    val photoURL: String?,

    @JsonProperty("description")
    val description: String?
)
