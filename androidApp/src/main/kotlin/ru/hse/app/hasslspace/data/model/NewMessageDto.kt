package ru.hse.app.hasslspace.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class NewMessageDto(
    @JsonProperty("content")
    val content: String?,

    @JsonProperty("createdAt")
    val createdAt: LocalDateTime,
)
