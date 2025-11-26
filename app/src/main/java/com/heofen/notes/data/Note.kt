package com.heofen.notes.data

import android.icu.text.CaseMap
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Instant

@Entity()
class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val text: String?,
    val createTime: Instant,
    val editTime: Instant? = null,
    val folderId: Int? = null
) {
    fun getFormattedTime(time: Instant?): String {
        return time?.let {
            DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")
                .format(java.time.LocalDateTime.ofInstant(it, java.time.ZoneId.systemDefault()))
        } ?: ""
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? {
        return date?.toEpochMilli()
    }
}