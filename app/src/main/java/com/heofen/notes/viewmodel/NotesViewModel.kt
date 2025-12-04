package com.heofen.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.heofen.notes.data.AppDatabase
import com.heofen.notes.data.Folder
import com.heofen.notes.data.Note
import com.heofen.notes.repository.FolderRepository
import com.heofen.notes.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val folders: List<Folder> = emptyList(),
    val selectedFolderId: Int? = null,
    val isLoading: Boolean = true
)

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val noteRepository = NoteRepository(database.noteDao())
    private val folderRepository = FolderRepository(database.folderDao())

    private val _selectedFolderId = MutableStateFlow<Int?>(null)
    val selectedFolderId: StateFlow<Int?> = _selectedFolderId.asStateFlow()

    val uiState: StateFlow<NotesUiState> = combine(
        noteRepository.getAllNotes(),
        folderRepository.getAllFolders(),
        _selectedFolderId
    ) { notes, folders, selectedFolder ->
        val filteredNotes = when (selectedFolder) {
            null -> notes
            -1 -> notes.filter { it.folderId == null }
            else -> notes.filter { it.folderId == selectedFolder }
        }
        NotesUiState(
            notes = filteredNotes,
            folders = folders,
            selectedFolderId = selectedFolder,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NotesUiState()
    )

    fun selectFolder(folderId: Int?) {
        _selectedFolderId.value = folderId
    }

    fun createNote(title: String?, text: String?) {
        viewModelScope.launch {
            val note = Note(
                title = title?.takeIf { it.isNotBlank() },
                text = text?.takeIf { it.isNotBlank() },
                createTime = System.currentTimeMillis(),
                folderId = _selectedFolderId.value?.takeIf { it != -1 }
            )
            noteRepository.insertNote(note)
        }
    }

    fun updateNote(noteId: Int, title: String?, text: String?) {
        viewModelScope.launch {
            noteRepository.updateNoteContent(
                noteId = noteId,
                title = title?.takeIf { it.isNotBlank() },
                text = text?.takeIf { it.isNotBlank() },
                editTime = System.currentTimeMillis()
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun moveNoteToFolder(noteId: Int, folderId: Int?) {
        viewModelScope.launch {
            noteRepository.moveNoteToFolder(noteId, folderId)
        }
    }

    fun createFolder(name: String) {
        viewModelScope.launch {
            val folder = Folder(name = name)
            folderRepository.insertFolder(folder)
        }
    }

    fun updateFolder(folder: Folder, newName: String) {
        viewModelScope.launch {
            folderRepository.updateFolder(folder.copy(name = newName))
        }
    }

    fun deleteFolder(folder: Folder) {
        viewModelScope.launch {
            noteRepository.moveNotesFromFolderToNull(folder.id)
            folderRepository.deleteFolder(folder)
        }
    }
}
