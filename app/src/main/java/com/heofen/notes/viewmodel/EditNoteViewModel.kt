package com.heofen.notes.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class EditNoteUiState(
    val noteId: Int = -1,
    val title: String = "",
    val text: String = ""
) {
    val isNew: Boolean get() = noteId == -1
    val isSaveEnabled: Boolean get() = title.isNotBlank() || text.isNotBlank()
}

class EditNoteViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        EditNoteUiState(
            noteId = savedStateHandle.get<Int>("NOTE_ID") ?: -1,
            title = savedStateHandle.get<String>("NOTE_TITLE") ?: "",
            text = savedStateHandle.get<String>("NOTE_TEXT") ?: ""
        )
    )
    val uiState: StateFlow<EditNoteUiState> = _uiState.asStateFlow()

    fun onTitleChange(newTitle: String) {
        _uiState.update { current ->
            val updated = current.copy(title = newTitle)
            saveToHandle(updated)
            updated
        }
    }

    fun onTextChange(newText: String) {
        _uiState.update { current ->
            val updated = current.copy(text = newText)
            saveToHandle(updated)
            updated
        }
    }

    private fun saveToHandle(state: EditNoteUiState) {
        savedStateHandle["NOTE_ID"] = state.noteId
        savedStateHandle["NOTE_TITLE"] = state.title
        savedStateHandle["NOTE_TEXT"] = state.text
    }
}
