package com.heofen.notes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Query("UPDATE notes SET title = :newTitle, text = :newText, editTime = :editTime WHERE id = :noteId")
    suspend fun updateContent(noteId: Int, newTitle: String?, newText: String?, editTime: Long)

    @Query("UPDATE notes SET folderId = :newFolderId WHERE id = :noteId")
    suspend fun moveToFolder(noteId: Int, newFolderId: Int?)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes ORDER BY COALESCE(editTime, createTime) DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE folderId = :folderId ORDER BY COALESCE(editTime, createTime) DESC")
    fun getNotesByFolder(folderId: Int): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE folderId IS NULL ORDER BY COALESCE(editTime, createTime) DESC")
    fun getNotesWithoutFolder(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?
}
