package ru.hse.app.hasslspace.domain.model.entity

import ru.hse.app.hasslspace.data.model.MessageDto
import ru.hse.app.hasslspace.data.roomstorage.MessageEntity
import java.time.LocalDateTime

data class Message(
    val id: String,
    val chatId: String,
    val userId: String,
    val content: String,
    val fileUrl: String?,
    val createdAt: LocalDateTime,
    val isRead: Boolean = false
)

fun MessageEntity.toDomain(): Message {
    return Message(
        id = this.id,
        chatId = this.chatId,
        userId = this.userId,
        content = this.content ?: "",
        fileUrl = this.fileUrl,
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
        fileUrl = this.fileUrl,
        createdAt = this.createdAt
    )
}

fun Message.toEntity(): MessageEntity {
    return MessageEntity(
        id = this.id,
        chatId = this.chatId,
        userId = this.userId,
        content = this.content,
        fileUrl = this.fileUrl,
        createdAt = this.createdAt,
        isRead = false
    )
}

fun MessageDto.toEntity(): MessageEntity {
    return MessageEntity(
        id = this.id,
        chatId = this.chatId,
        userId = this.userId,
        content = this.content ?: "",
        fileUrl = this.fileUrl,
        createdAt = this.createdAt
    )
}
