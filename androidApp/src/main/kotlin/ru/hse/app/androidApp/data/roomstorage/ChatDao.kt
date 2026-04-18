package ru.hse.app.androidApp.data.roomstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Query(
        """
        SELECT * FROM message
        WHERE chat_id = :chatId
        ORDER BY created_at ASC
    """
    )
    fun observeMessagesByChatId(chatId: String): Flow<List<MessageEntity>>

    @Query(
        """
        SELECT * FROM message
        WHERE chat_id = :chatId
        ORDER BY created_at ASC
    """
    )
    suspend fun getMessagesByChatId(chatId: String): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Query("UPDATE message SET is_read = 1 WHERE chat_id = :chatId AND is_read = 0")
    suspend fun markAllMessagesAsRead(chatId: String)

    @Query("UPDATE message SET is_read = 1 WHERE id = :messageId")
    suspend fun markMessageAsRead(messageId: String)

    @Query("SELECT COUNT(*) FROM message WHERE chat_id = :chatId AND is_read = 0")
    fun observeUnreadCount(chatId: String): Flow<Int>
}