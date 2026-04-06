package ru.hse.app.androidApp.domain.model.entity

import ru.hse.app.androidApp.data.model.MessageDto
import ru.hse.app.androidApp.data.roomstorage.MessageEntity
import java.time.LocalDateTime

data class Message(
    val id: String,
    val chatId: String,
    val userId: String,
    val content: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean = false
)

fun MessageEntity.toDomain(): Message {
    return Message(
        id = this.id,
        chatId = this.chatId,
        userId = this.userId,
        content = this.content ?: "",
        createdAt = this.createdAt,
        isRead = this.isRead
    )
}

fun MessageDto.toDomain(): Message {
    return Message(
        id = this.id,
        chatId = this.chatId,
        userId = this.userId,
        content = this.content ?: "",
        createdAt = this.createdAt
    )
}
