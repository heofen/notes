package com.heofen.notes.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.format.DateTimeFormatter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = Folder::class,
            parentColumns = ["id"],
            childColumns = ["folderId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val text: String?,
    val createTime: Long,
    val editTime: Long? = null,
    val folderId: Int? = null
) {
    fun getFormattedCreateTime(): String {
        return formatTime(createTime)
    }

    fun getFormattedEditTime(): String? {
        return editTime?.let { formatTime(it) }
    }

    private fun formatTime(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return DateTimeFormatter.ofPattern("HH:mm dd.MM.yy").format(dateTime)
    }

    fun getDisplayTime(): String {
        return getFormattedEditTime() ?: getFormattedCreateTime()
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
