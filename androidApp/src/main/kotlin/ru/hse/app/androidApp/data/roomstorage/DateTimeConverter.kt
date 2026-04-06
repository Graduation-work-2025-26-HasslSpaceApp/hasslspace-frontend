package ru.hse.app.androidApp.data.roomstorage

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneId

class DateTimeConverter {

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): Long? {
        return value
            ?.atZone(ZoneId.systemDefault())
            ?.toInstant()
            ?.toEpochMilli()
    }

    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime? {
        return value
            ?.let { java.time.Instant.ofEpochMilli(it) }
            ?.atZone(ZoneId.systemDefault())
            ?.toLocalDateTime()
    }
}