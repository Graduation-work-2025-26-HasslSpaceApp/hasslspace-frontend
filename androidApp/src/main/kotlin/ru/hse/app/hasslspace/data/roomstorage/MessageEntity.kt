package ru.hse.app.hasslspace.data.roomstorage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "message",
    indices = [
        Index(value = ["chat_id"]),
        Index(value = ["chat_id", "created_at"])
    ]
)
data class MessageEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "chat_id")
    val chatId: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "content")
    val content: String?,

    @ColumnInfo(name = "file_url")
    val fileUrl: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime,

    @ColumnInfo(name = "is_read")
    val isRead: Boolean = false
)