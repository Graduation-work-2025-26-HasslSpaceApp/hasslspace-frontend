package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class InvitationDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("link")
    val link: String,

    @JsonProperty("expTime")
    val expTime: LocalDateTime
)