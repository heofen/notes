package com.heofen.notes.repository

import com.heofen.notes.data.Note
import com.heofen.notes.data.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    fun getNotesByFolder(folderId: Int): Flow<List<Note>> = noteDao.getNotesByFolder(folderId)

    fun getNotesWithoutFolder(): Flow<List<Note>> = noteDao.getNotesWithoutFolder()

    suspend fun getNoteById(noteId: Int): Note? = noteDao.getNoteById(noteId)

    suspend fun insertNote(note: Note): Long = noteDao.insert(note)

    suspend fun updateNote(note: Note) = noteDao.update(note)

    suspend fun updateNoteContent(noteId: Int, title: String?, text: String?, editTime: Long) {
        noteDao.updateContent(noteId, title, text, editTime)
    }

    suspend fun moveNoteToFolder(noteId: Int, folderId: Int?) {
        noteDao.moveToFolder(noteId, folderId)
    }

    suspend fun deleteNote(note: Note) = noteDao.delete(note)

    suspend fun moveNotesFromFolderToNull(folderId: Int) {
        noteDao.moveNotesFromFolderToNull(folderId)
    }
}
