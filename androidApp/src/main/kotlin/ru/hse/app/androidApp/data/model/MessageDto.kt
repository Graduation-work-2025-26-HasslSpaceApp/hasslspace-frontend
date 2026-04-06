package ru.hse.app.androidApp.data.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class MessageDto(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("chatId")
    val chatId: String,

    @JsonProperty("userId")
    val userId: String,

    @JsonProperty("content")
    val content: String?,

    @JsonProperty("createdAt")
    val createdAt: LocalDateTime,
)
