package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class InvitationDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("userId")
    val userId: String,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("username")
    val username: String,

    @JsonProperty("status")
    val status: String,

    @JsonProperty("photoUrl")
    val photoURL: String?,

    @JsonProperty("expTime")
    val expTime: LocalDateTime
)