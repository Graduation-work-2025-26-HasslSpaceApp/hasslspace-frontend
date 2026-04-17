package ru.hse.app.androidApp.data.roomstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MessageEntity::class],
    version = 2,
    exportSchema = true
)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): ChatDao
}